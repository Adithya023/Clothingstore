package service;

import service.custom.impl.*;
import util.Type;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

//    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return serviceFactory==null? new ServiceFactory():serviceFactory;
    }

    public <T extends SuperService>T getServiceType(Type type){
        switch (type){
            case CUSTOMER:return (T) new CustomerServiceImpl();
            case PRODUCT:return (T) new ProductServiceImpl();
            case SUPPLIER:return(T) new SupplierServiceImpl();
            case CATEGORY:return (T) new CategoryServiceImpl();
            case ADMIN:return (T) new AdminServiceImpl();
            case EMPLOYEE:return (T) new EmployeeServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
            case RETURNORDER:return (T) new ReturnOrderServiceImpl();
            case ORDERITEMS:return (T) new OrderItemsServiceImpl();
        }
        return null;
    }
}
