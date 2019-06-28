package com.kgc.entity;
import java.io.Serializable;

/**  
 * 对应表 tbl_stud
 *
 * @author lgs  
 *  
 */ 
  
public class Stud implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /*
         对应表中id
         
   */  


   
   private Integer id;
    /*
         对应表中name
         
   */  
   
   
   private String name;
    /*
         对应表中age
         
   */  
   
   
   private Integer age;
    /*
         对应表中address
         
   */  
   
   
   private String address;
    /*
         对应表中cid
         
   */  
   
   
   private Integer cid;
    /*
         对应表中pic
         
   */  
   
   
   private String pic;
    public Stud(){
    } 
    public Integer getId(){    
      return id;    
    }    
    public void setId(Integer id){    
      this.id = id;    
    }    
    public String getName(){    
      return name;    
    }    
    public void setName(String name){    
      this.name = name;    
    }    
    public Integer getAge(){    
      return age;    
    }    
    public void setAge(Integer age){    
      this.age = age;    
    }    
    public String getAddress(){    
      return address;    
    }    
    public void setAddress(String address){    
      this.address = address;    
    }    
    public Integer getCid(){    
      return cid;    
    }    
    public void setCid(Integer cid){    
      this.cid = cid;    
    }    
    public String getPic(){    
      return pic;    
    }    
    public void setPic(String pic){    
      this.pic = pic;    
    }    
  
  
      
}   