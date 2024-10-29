package repository;

import repository.custom.impl.*;
import util.Type;

public class RepositoryFactory {
    private static RepositoryFactory repositoryFactory;
    private RepositoryFactory(){}

    public static RepositoryFactory getInstance() {
        return repositoryFactory==null?repositoryFactory=new RepositoryFactory():repositoryFactory;
    }
    public <T extends SuperRepository>T getRepositoryType(Type type){
        switch (type){
            case CUSTOMER: return (T) new CustomerRepositoryImpl();
            case PRODUCT:return (T) new ProductRepositoryImpl();
            case SUPPLIER:return (T) new SupplierRepositoryImpl();
            case CATEGORY:return (T) new CategoryRepositoryImpl();
            case ADMIN:return (T) new AdminRepositoryImpl();
            case EMPLOYEE:return (T) new EmployeeRepositoryImpl();
            case ORDER:return (T) new OrderRepositoryImpl();
            case RETURNORDER:return (T) new ReturnOrderRepositoryImpl();
            case ORDERITEMS:return (T) new OrderItemsRepositoryImpl();
        }
        return null;
    }

}
