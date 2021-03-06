package tester;

import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        TypedQuery query;
        try {
            em.getTransaction().begin();
            em.persist(new Employee("xa12tt", "Kurt", "Wonnegut", new BigDecimal(335567)));
            em.persist(new Employee("hyu654", "Hanne", "Olsen", new BigDecimal(435867)));
            em.persist(new Employee("uio876", "Jan", "Olsen", new BigDecimal(411567)));
            em.persist(new Employee("klo999", "Irene", "Petersen", new BigDecimal(33567)));
            em.persist(new Employee("jik666", "Tian", "Wonnegut", new BigDecimal(56567)));
            em.getTransaction().commit();
            
            //Complete all these small tasks. Your will find the solution to all, but the last,
            //In this document: https://en.wikibooks.org/wiki/Java_Persistence/JPQL#JPQL_supported_functions
            
            //1) Create a query to fetch all employees with a salary > 100000 and print out all the salaries
            
            query = em.createQuery("SELECT e FROM Employee e WHERE e.salary > :min", Employee.class);
            query.setParameter("min", 100000);
            List<Employee> result = query.getResultList();
            System.out.println("\nTASK 1");
            for (Employee e : result) {
                System.out.println(e.getFirstName() + " : " + e.getSalary() + " kr.");
            }
            
            //2) Create a query to fetch the employee with the id "klo999" and print out the firstname
            
             query = em.createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class);
             query.setParameter("id", "klo999");
             Employee employee1 = (Employee) query.getSingleResult();
             System.out.println("\nTASK 2\n" + employee1.getFirstName() + "\n");
            
            //3) Create a query to fetch the highest salary and print the value
            
            query = em.createQuery("SELECT MAX(e.salary) FROM Employee e", Employee.class);
            BigDecimal maxSalary = (BigDecimal) query.getSingleResult();
            System.out.println("TASK 3\nMax salary is: " + maxSalary + "\n");

            //4) Create a query to fetch the firstName of all Employees and print the names
            
            query = em.createQuery("SELECT e.firstName FROM Employee e", Employee.class);
            List<String> names = query.getResultList();
            System.out.println("TASK 4");
            for (String name : names) {
                System.out.println(name);
            }
           
            //5 Create a query to calculate the number of employees and print the number
            
            query = em.createQuery("SELECT COUNT(e) FROM Employee e", Employee.class);
            long numberOfEmployees = (long) query.getSingleResult();
            System.out.println("\nTASK 5\n" + "Number of employees: " + numberOfEmployees);
            
            //6 Create a query to fetch the Employee with the higest salary and print all his details
            query = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)", Employee.class);
            Employee employee2 = (Employee) query.getSingleResult();
            System.out.println("\nTASK 6\n" + employee2.toString() + "\n");
            
        } finally {
            em.close();
            emf.close();
        }
    }

}
