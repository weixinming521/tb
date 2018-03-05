package com.travel.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;  
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.travel.core.utils.ReflectionUtils;

 

@Service(value="baseMongoService")
public class BaseMongoService<T>{  
      
   /** 
    * spring mongodb　集成操作类　 
    */  
   @Autowired
   protected MongoTemplate mongoTemplate;  
   
   public List<T> find(Query query) {  
       return mongoTemplate.find(query, this.getEntityClass());  
   }
   public List<T> find(Query query, String collectionName) {  
       return mongoTemplate.find(query, this.getEntityClass(), collectionName);
   }
 
   public T findOne(Query query) {  
       return mongoTemplate.findOne(query, this.getEntityClass());  
   }  
   public T findOne(Query query, String collectionName) {  
       return mongoTemplate.findOne(query, this.getEntityClass(), collectionName);
   }
   public void update(Query query, Update update) {  
       mongoTemplate.findAndModify(query, update, this.getEntityClass());  
   }  
   public void update(Query query, Update update, String collectionName) {  
       mongoTemplate.findAndModify(query, update, this.getEntityClass() ,collectionName);  
   } 
   public T save(T entity , String collectionName) {  
       mongoTemplate.insert(entity, collectionName);       
       return entity;  
   } 
   public T save(T entity ) {  
       mongoTemplate.insert(entity);
       return entity;  
   }  
 
   public T findById(String id) {  
       return mongoTemplate.findById(id, this.getEntityClass());  
   }  
 
   public T findById(String id, String collectionName) {  
       return mongoTemplate.findById(id, this.getEntityClass(), collectionName);  
   }
   public T findAndRemove(Query query , String collectionName) {  
       return mongoTemplate.findAndRemove(query, this.getEntityClass(), collectionName);
   } 
   public List<T> findAllAndRemove(Query query , String collectionName) {  
       return mongoTemplate.findAllAndRemove(query, this.getEntityClass(), collectionName);
   }  
   public List<T> findPage(Query query , Integer pageNumber,Integer pageSize ){  
       query.skip((pageNumber - 1) * pageSize).limit(pageSize);  
       List<T> rows = this.find(query);  
       return rows;  
   }  
   public List<T> findPage(Query query , String collectionName, Integer pageNumber,Integer pageSize ){  
       query.skip((pageNumber - 1) * pageSize).limit(pageSize);  
       List<T> rows = this.find(query,collectionName);  
       return rows;  
   }
   public long count(Query query, String collectionName){  
       return mongoTemplate.count(query, this.getEntityClass(),collectionName);  
   }  
   public long count(Query query){  
       return mongoTemplate.count(query, this.getEntityClass());  
   }
   public  GroupByResults<T> group(Criteria criteria, String collectionName , GroupBy groupBy ){  
       GroupByResults<T> group = mongoTemplate.group(criteria, collectionName, groupBy, this.getEntityClass());    
       return group;
   }
   
   public  void dropCollection( String collectionName ){  
      mongoTemplate.dropCollection(collectionName);
   } 
   
   /** 
    * 获取需要操作的实体类class 
    *  
    * @return 
    */  
   private Class<T> getEntityClass(){  
       return ReflectionUtils.getSuperClassGenricType(getClass());  
   }  
  
   
}
