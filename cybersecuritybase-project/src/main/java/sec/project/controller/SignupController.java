package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;
    
    private String database = "jdbc:h2:./database";
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        if (name.isEmpty() || address.isEmpty()) return "redirect:/form";
        try {
            Connection connection = DriverManager.getConnection(database, "", "");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Signup(name, address) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, address);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "redirect:/stats";
    }
    
    @RequestMapping(value = "/stats") 
    public String signups(Model model) {
        List<Signup> signups = new ArrayList();
        try {
            Connection connection = DriverManager.getConnection(database, "", "");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Signup");
            while (result.next()) {
                String name = result.getString("name");
                String address = result.getString("address");
                signups.add(new Signup(name, address));
            }
            statement.close();
            result.close();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        model.addAttribute("signups", signups);
        return "signups";
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(required = false) Integer admin) {
        if (admin == null) {
            return "Blocked";
        }
        if (admin != 1) {
            return "Blocked";
        }
        return "search";
    }

    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchResults(@RequestParam String name, Model model) {
        List<Signup> signups = new ArrayList();
        try {
            Connection connection = DriverManager.getConnection(database, "", "");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Signup WHERE name = '" + name + "'");
            while (result.next()) {
                String user = result.getString("name");
                String address = result.getString("address");
                signups.add(new Signup(user, address));
            }
            statement.close();
            result.close();
            connection.close();
        } catch (Exception ex) {
            // Ignore in silence #codingbad
        }
        model.addAttribute("signups", signups);
        return "signups";
    }
    
}
