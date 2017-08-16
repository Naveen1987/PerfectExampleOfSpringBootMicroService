package com.example.demo;


import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableEurekaClient
@RestController
public class StudentMicroApplication {
	@Bean
	CommandLineRunner commandlinerunner(studentdao dao){
		return records->Stream.of("A|1000","B|2000","C|3000","D|4000","E|5000","F|6000").forEach(
				value->{
					String sname=value.split(Pattern.quote("|"))[0];
							double sfee=Double.valueOf(value.split(Pattern.quote("|"))[1]);
					dao.save(new Student(sname, sfee));				
				}
				);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(StudentMicroApplication.class, args);
	}
	
	@Autowired
	studentdao dao;
	
	@GetMapping("/")
	public Iterable<Student> getIndex()
	{
		return dao.findAll();
	}
}

@RepositoryRestResource
interface studentdao extends CrudRepository<Student, Integer>{
	
}

@Entity
class Student{
	@Id
	@GeneratedValue
	private int sid;
	private String sname;
	private double sfee;
	
	public Student() {//For JPA
		
	}
	
	public Student(String sname, double sfee) {
		
		this.sname = sname;
		this.sfee = sfee;
	}

	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public double getSfee() {
		return sfee;
	}
	public void setSfee(double sfee) {
		this.sfee = sfee;
	}
	@Override
	public String toString() {
		return "Student [sid=" + sid + ", sname=" + sname + ", sfee=" + sfee + "]";
	}
	
}
