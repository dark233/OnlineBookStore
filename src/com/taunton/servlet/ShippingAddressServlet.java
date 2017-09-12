package com.taunton.servlet;

import org.apache.log4j.Logger;

import com.taunton.service.ShippingAddressService;
import com.taunton.utils.DomainFactory;

public class ShippingAddressServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserServlet.class); 
	private ShippingAddressService sas = DomainFactory.createDomainInstance(ShippingAddressService.class);
}
