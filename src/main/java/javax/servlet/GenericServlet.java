/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*pmz 2019-01-08- 15:02:22
GenericServlet类主要是通过容器传入的表示配置信息的ServletConfig 类获取了其中的一些信息,主要
实现了 init()方法.destory()方法,以及方便性的给出了得到 ServletContext 上下文对象 / 得到
配置的属性参数枚举.
*/


package javax.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 * Defines a generic, protocol-independent
 * servlet. To write an HTTP servlet for use on the
 * Web, extend {@link javax.servlet.http.HttpServlet} instead.
 *
 * <p><code>GenericServlet</code> implements the <code>Servlet</code>
 * and <code>ServletConfig</code> interfaces. <code>GenericServlet</code>
 * may be directly extended by a servlet, although it's more common to extend
 * a protocol-specific subclass such as <code>HttpServlet</code>.
 *
 * <p><code>GenericServlet</code> makes writing servlets
 * easier. It provides simple versions of the lifecycle methods
 * <code>init</code> and <code>destroy</code> and of the methods
 * in the <code>ServletConfig</code> interface. <code>GenericServlet</code>
 * also implements the <code>log</code> method, declared in the
 * <code>ServletContext</code> interface.
 *
 * <p>To write a generic servlet, you need only
 * override the abstract <code>service</code> method.
 *
 *
 * @author 	Various
 */


public abstract class GenericServlet
    implements Servlet, ServletConfig, java.io.Serializable
{
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);
    // 设置瞬态的 配置类 变量
    private transient ServletConfig config;


    /**
     *
     * Does nothing. All of the servlet initialization
     * is done by one of the <code>init</code> methods.
     *
     */
    // 不做任何处理,初始化工作将全部交由 init()
    public GenericServlet() { }


    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being taken out of service.  See {@link Servlet#destroy}.
     *
     *
     */
    // 这里没有具体实现???
    public void destroy() {
    }


    /**
     * Returns a <code>String</code> containing the value of the named
     * initialization parameter, or <code>null</code> if the parameter does
     * not exist.  See {@link ServletConfig#getInitParameter}.
     *
     * <p>This method is supplied for convenience. It gets the
     * value of the named parameter from the servlet's
     * <code>ServletConfig</code> object.
     *
     * @param name 		a <code>String</code> specifying the name
     *				of the initialization parameter
     *
     * @return String 		a <code>String</code> containing the value
     *				of the initialization parameter
     *
     */
    // 从 Servletconfig 对象通过其方法提取出初始化参数中名字所对应的值,或者 null
    public String getInitParameter(String name) {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getInitParameter(name);
    }


   /**
    * Returns the names of the servlet's initialization parameters
    * as an <code>Enumeration</code> of <code>String</code> objects,
    * or an empty <code>Enumeration</code> if the servlet has no
    * initialization parameters.  See {@link
    * ServletConfig#getInitParameterNames}.
    *
    * <p>This method is supplied for convenience. It gets the
    * parameter names from the servlet's <code>ServletConfig</code> object.
    *
    *
    * @return Enumeration 	an enumeration of <code>String</code>
    *				objects containing the names of
    *				the servlet's initialization parameters
    */
   // 从 ServletConfig 对象中通过其方法提取出初始化参数枚举
    public Enumeration<String> getInitParameterNames() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getInitParameterNames();
    }


    /**
     * Returns this servlet's {@link ServletConfig} object.
     *
     * @return ServletConfig 	the <code>ServletConfig</code> object
     *				that initialized this servlet
     */
    // 获得配置对象
    public ServletConfig getServletConfig() {
	return config;
    }


    /**
     * Returns a reference to the {@link ServletContext} in which this servlet
     * is running.  See {@link ServletConfig#getServletContext}.
     *
     * <p>This method is supplied for convenience. It gets the
     * context from the servlet's <code>ServletConfig</code> object.
     *
     *
     * @return ServletContext 	the <code>ServletContext</code> object
     *				passed to this servlet by the <code>init</code>
     *				method
     */
    // 从 ServletConfig对象中通过其方法获得的 ServletContext上下文对象.
    public ServletContext getServletContext() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getServletContext();
    }


    /**
     * Returns information about the servlet, such as
     * author, version, and copyright.
     * By default, this method returns an empty string.  Override this method
     * to have it return a meaningful value.  See {@link
     * Servlet#getServletInfo}.
     *
     *
     * @return String 		information about this servlet, by default an
     * 				empty string
     */
    // 默认返回""
    public String getServletInfo() {
	return "";
    }


    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being placed into service.  See {@link Servlet#init}.
     *
     * <p>This implementation stores the {@link ServletConfig}
     * object it receives from the servlet container for later use.
     * When overriding this form of the method, call
     * <code>super.init(config)</code>.
     *
     * @param config 			the <code>ServletConfig</code> object
     *					that contains configuration
     *					information for this servlet
     *
     * @exception ServletException 	if an exception occurs that
     *					interrupts the servlet's normal
     *					operation
     *
     * @see 				UnavailableException
     */
    // 从Servlet容器中接收到 ServletConfig 对象,并存在瞬态属性变量 config 中供其他该类中的其他方法进行使用.
    // 这意味着容器会在运行时将 ServletConfig对象传入进行初始化.
    public void init(ServletConfig config) throws ServletException {
	this.config = config;
	this.init();
    }


    /**
     * A convenience method which can be overridden so that there's no need
     * to call <code>super.init(config)</code>.
     *
     * <p>Instead of overriding {@link #init(ServletConfig)}, simply override
     * this method and it will be called by
     * <code>GenericServlet.init(ServletConfig config)</code>.
     * The <code>ServletConfig</code> object can still be retrieved via {@link
     * #getServletConfig}.
     *
     * @exception ServletException 	if an exception occurs that
     *					interrupts the servlet's
     *					normal operation
     */
    // 不做具体工作,留给子类去按照需求重写,定义所需要的初始化行为.
    public void init() throws ServletException {

    }


    /**
     * Writes the specified message to a servlet log file, prepended by the
     * servlet's name.  See {@link ServletContext#log(String)}.
     *
     * @param msg 	a <code>String</code> specifying
     *			the message to be written to the log file
     */
    //通过上下文对象的 log 方法来生成 log
    public void log(String msg) {
	getServletContext().log(getServletName() + ": "+ msg);
    }


    /**
     * Writes an explanatory message and a stack trace
     * for a given <code>Throwable</code> exception
     * to the servlet log file, prepended by the servlet's name.
     * See {@link ServletContext#log(String, Throwable)}.
     *
     *
     * @param message 		a <code>String</code> that describes
     *				the error or exception
     *
     * @param t			the <code>java.lang.Throwable</code> error
     * 				or exception
     */
    public void log(String message, Throwable t) {
	getServletContext().log(getServletName() + ": " + message, t);
    }


    /**
     * Called by the servlet container to allow the servlet to respond to
     * a request.  See {@link Servlet#service}.
     *
     * <p>This method is declared abstract so subclasses, such as
     * <code>HttpServlet</code>, must override it.
     *
     * @param req 	the <code>ServletRequest</code> object
     *			that contains the client's request
     *
     * @param res 	the <code>ServletResponse</code> object
     *			that will contain the servlet's response
     *
     * @exception ServletException 	if an exception occurs that
     *					interferes with the servlet's
     *					normal operation occurred
     *
     * @exception IOException 		if an input or output
     *					exception occurs
     */
    //抽象的定义了 service 方法
    public abstract void service(ServletRequest req, ServletResponse res)
	throws ServletException, IOException;


    /**
     * Returns the name of this servlet instance.
     * See {@link ServletConfig#getServletName}.
     *
     * @return          the name of this servlet instance
     */
    // 通过 ServletConfig 对象来获得 Servlet 名称字符串.
    public String getServletName() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(
                lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getServletName();
    }
}
