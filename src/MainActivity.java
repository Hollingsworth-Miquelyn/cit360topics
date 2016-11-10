package com.miquelynhollingsworth.diary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miquelynhollingsworth.diary.models.Users;
import java.util.List;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private static SessionFactory factory;
    public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    MainActivity register = new MainActivity();


    /* Add few employee records in database */
    Integer empID1 = register.addUser("Miquelyn", "Hollingsworth", "miq10", "miq10@yahoo.com", "pass1234", "123-456-7890");
    Integer empID2 = register.addUser("Dave", "Hollingsworth", "dave98", "dave10@yahoo.com", "pass1234", "123-456-7890");
    Integer empID3 = register.addUser("Susan", "Hollingsworth", "sus10", "sus10@yahoo.com", "pass1234", "123-456-7890");

      /* List down all the employees */
//    register.listUsers();

      /* Update employee's records */
//    register.updateUser(empID1, "miq2005");

      /* Delete an employee from the database */
//    register.deleteUser(empID3);

      /* List down new list of the employees */
//    register.listUsers();
}
    /* Method to CREATE a user in the database */
    public Integer addUser(String fname, String lname, String uname, String email, String password, String phone) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;
        try{
            tx = session.beginTransaction();
            Users user = new Users(fname, lname, uname, email, password, phone);
            userID = (Integer) session.save(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return userID;
    }
    /* Method to  READ all the users */
/*    public void listUsers(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List users = session.createQuery("FROM Employee").list();
            for (Iterator iterator =
                 users.iterator(); iterator.hasNext();){
                Users user = (Users) iterator.next();
                System.out.print("First Name: " + user.getFirstName());
                System.out.print("  Last Name: " + user.getLastName());
                System.out.println("  Username: " + user.getUsername());
                System.out.println("  Email: " + user.getEmail());
                System.out.println("  Password: " + user.getPassword());
                System.out.println("  Phone Number: " + user.getPhoneNumber());

            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to UPDATE email for a user */
/*    public void updateUser(Integer EmployeeID, String email ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Users user =
                    (Users) session.get(Users.class, EmployeeID);
            user.setEmail( email );
            session.update(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE a user from the records */
 /*   public void deleteUser(Integer userId){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Users user =
                    (Users) session.get(Users.class, userId);
            session.delete(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
*/
}
