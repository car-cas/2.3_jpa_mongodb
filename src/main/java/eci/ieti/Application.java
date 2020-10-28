package eci.ieti;

import eci.ieti.data.CustomerRepository;
import eci.ieti.data.ProductRepository;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.UserRepository;
import eci.ieti.data.model.Customer;
import eci.ieti.data.model.Product;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        customerRepository.deleteAll();
        userRepository.deleteAll();
        todoRepository.deleteAll();

        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

        User user1 = userRepository.save(new User(1L,"Admin","admin@mail.com"));
        User user2 = userRepository.save(new User(2L,"Carlos","carlos@mail.com"));
        User user3 = userRepository.save(new User(3L,"Luis","luis@mail.com"));
        User user4 = userRepository.save(new User(4L,"Fernan","floo@mail.com"));
        User user5 = userRepository.save(new User(5L,"Alejandra","alejandra@mail.com"));
        User user6 = userRepository.save(new User(6L,"Juan","juan@mail.com"));
        User user7 = userRepository.save(new User(7L,"Camilo","camilo@mail.com"));
        User user8 = userRepository.save(new User(8L,"Andres","andres@mail.com"));
        User user9 = userRepository.save(new User(9L,"majo","majo@mail.com"));
        User user10 = userRepository.save(new User(10L,"pepe","elpepe@mail.com"));

        todoRepository.save(new Todo("task1", 1,new Date(120,9,23),user1,"Ready"));
        todoRepository.save(new Todo("task2", 7,new Date(120,9,1),user2,"In Progress"));
        todoRepository.save(new Todo("task3", 3,new Date(120,9,2),user3,"Ready"));
        todoRepository.save(new Todo("task4", 4,new Date(120,9,3),user4,"pending"));
        todoRepository.save(new Todo("task5", 5,new Date(120,9,4),user5,"Ready"));
        todoRepository.save(new Todo("task6", 6,new Date(120,9,29),user6,"pending"));
        todoRepository.save(new Todo("task7", 7,new Date(120,9,30),user7,"In Progress"));
        todoRepository.save(new Todo("task8", 8,new Date(120,9,31),user8,"Ready"));
        todoRepository.save(new Todo("task9", 9,new Date(120,10,1),user1,"pending"));
        todoRepository.save(new Todo("task10", 10,new Date(120,10,2),user1,"pending"));
        todoRepository.save(new Todo("task11", 1,new Date(120,11,24),user1,"pending"));
        todoRepository.save(new Todo("task12", 2,new Date(120,10,20),user2,"Ready"));
        todoRepository.save(new Todo("task13", 3,new Date(120,10,9),user1,"pending"));
        todoRepository.save(new Todo("task14", 4,new Date(120,10,10),user4,"In Progress"));
        todoRepository.save(new Todo("task15", 5,new Date(120,10,12),user5,"Ready"));
        todoRepository.save(new Todo("task16", 6,new Date(120,10,13),user1,"pending"));
        todoRepository.save(new Todo("task17", 7,new Date(120,10,14),user7,"In Progress"));
        todoRepository.save(new Todo("task19", 8,new Date(120,10,15),user8,"pending"));
        todoRepository.save(new Todo("task20", 9,new Date(120,10,16),user9,"Ready"));
        todoRepository.save(new Todo("task21pruebade30caracteresenunadescripcion", 10,new Date(120,10,27),user10,"pending"));
        todoRepository.save(new Todo("task22", 1,new Date(120,10,10),user1,"In Progress"));
        todoRepository.save(new Todo("task23", 8,new Date(120,10,11),user2,"Ready"));
        todoRepository.save(new Todo("task24", 3,new Date(120,11,13),user1,"pending"));
        todoRepository.save(new Todo("task25", 4,new Date(120,11,25),user4,"pending"));

        //Query 1
        Query query = new Query();
        query.addCriteria(Criteria.where("dueDate").lt(new Date()));

        List<Todo> expired = mongoOperation.find(query,Todo.class);
        System.out.println("Query 1: " +expired);

        //Query 2
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("priority").gt(4).andOperator(Criteria.where("responsible").is(user2)));
        List<Todo> todos=mongoOperation.find(query2,Todo.class);
        System.out.println("Query 2: " +todos);

        //Query 3
        System.out.println("Query 3: ");
        userRepository.findAll().stream().forEach(user -> {
            Query queryt = new Query();
            queryt.addCriteria(Criteria.where("responsible").is(user));
            if (mongoOperation.find(queryt, Todo.class).size() >= 2)
                System.out.print(user.getEmail()+", ");
        });
        System.out.println();

        //Query 4

        Query query4 = new Query();
        query4.addCriteria(Criteria.where("description").regex(".{30,}"));
        List<Todo> todos3 = mongoOperation.find(query4,Todo.class);
        System.out.println("Query 4: " +todos3);

        /**
        System.out.println("-------------------------------");
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        
        customerRepository.findAll().stream().forEach(System.out::println);
        System.out.println();*/
        
        productRepository.deleteAll();

        productRepository.save(new Product(1L, "Samsung S8", "All new mobile phone Samsung S8"));
        productRepository.save(new Product(2L, "Samsung S8 plus", "All new mobile phone Samsung S8 plus"));
        productRepository.save(new Product(3L, "Samsung S9", "All new mobile phone Samsung S9"));
        productRepository.save(new Product(4L, "Samsung S9 plus", "All new mobile phone Samsung S9 plus"));
        productRepository.save(new Product(5L, "Samsung S10", "All new mobile phone Samsung S10"));
        productRepository.save(new Product(6L, "Samsung S10 plus", "All new mobile phone Samsung S10 plus"));
        productRepository.save(new Product(7L, "Samsung S20", "All new mobile phone Samsung S20"));
        productRepository.save(new Product(8L, "Samsung S20 plus", "All new mobile phone Samsung S20 plus"));
        productRepository.save(new Product(9L, "Samsung S20 ultra", "All new mobile phone Samsung S20 ultra"));

        /**
        System.out.println("Paginated search of products by criteria:");
        System.out.println("-------------------------------");
        
        productRepository.findByDescriptionContaining("plus", PageRequest.of(0, 2)).stream()
        	.forEach(System.out::println);
   
        System.out.println();*/
    }

}