/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.tengen;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.sun.org.apache.xml.internal.security.encryption.AgreementMethod;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Week1Homework4 {

    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        DB database = client.getDB("school");
        final DBCollection collection = database.getCollection("students");

        DBCursor cur = collection.find();

        while (cur.hasNext()) {

            DBObject object = cur.next();

            // HashMap<String, Object> score = new HashMap<String, Object>();
            HashMap<String, Object> scoreLong = null;

            ArrayList< HashMap<String, Object>> scores = (ArrayList<HashMap<String, Object>>) object.get("scores");

            ArrayList< HashMap<String, Object>> modify = new ArrayList<HashMap<String, Object>>();

            for (HashMap<String, Object> scoreIterator : scores) {

                // System.out.println(scoreIterator);
                modify.add(scoreIterator);

                if (scoreIterator.containsValue("homework")) {

                    if (scoreLong == null) {

                        scoreLong = scoreIterator;

                    } else {
                        if ((Double) scoreLong.get("score") > (Double) scoreIterator.get("score")) {
                            modify.remove(scoreIterator);
                        } else {
                            modify.remove(scoreLong);
                        }
                    }

                }

            }

            collection.remove(object);
                object.put("scores", modify);
//             
             collection.insert(object);                          
        }

        cur = collection.find();

        while (cur.hasNext()) {

             System.out.println(cur.next());
        }

//        int timesChange = 0;
//         DBCursor cur = collection.find(new BasicDBObject("type", "homework")).sort(new BasicDBObject("student_id", 1));
//         Object changeId = cur.next().get("student_id");
//         Object iterateChangeId;
//         
//         while(cur.hasNext()){
//             
//             iterateChangeId = cur.next().get("student_id");
//             if(!changeId.equals(iterateChangeId)){
//                 //System.out.println(changeId);
//                 changeId = iterateChangeId;
//                 timesChange++;
//             }                                       
//         }
//         
//         cur = collection.find();
//         
//         BasicDBObject query = new BasicDBObject("score", 1);
//         
//         
//         for(int i=0; i<200; i++){
//             
//           collection.remove(collection.find(new BasicDBObject("student_id",i).append("type", "homework")).sort(query).next());
//             
//             
//             
//         }
//         
//         
//         System.out.println(collection.find().count());
//         
//         
    }
}
