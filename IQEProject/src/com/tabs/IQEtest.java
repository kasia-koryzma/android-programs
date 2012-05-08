package com.tabs;

import com.iqengines.javaiqe.javaiqe;
import com.iqengines.javaiqe.javaiqe.IQEQuery;
import java.io.File;
import java.util.ArrayList;

/**
 * IQEngines Java API
 *
 * test file
 *
 * @author Vincent Garrigues
 */
public class IQEtest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String KEY = "64bc6a7fd77643899d3af8b3059ae158";
        final String SECRET = "c27e162ea7c24c619f85001454611498";

        /*
         * An API object is initialized using the API key and secret
         */
        iqe = new javaiqe(KEY, SECRET);

        /*
         * You can quickly query an image and retrieve results by doing:
         */
        
        File test_file = new File("/res/drawable/apple/jpg");
        
        // Query
        IQEQuery query = iqe.query(test_file);

        System.out.println("query.result : " + query.getResult());
        System.out.println("query.qid : " + query.getQID());
        
        // Update
        String update = iqe.update();
        System.out.println("Update : " + update);

        // Result
        String result = iqe.result(query.getQID(), true);
        System.out.println("Result : " + result);

        /*
        // Upload
        uploadObject();
        */
    }

    /**
     * Sample code for uploading an object
     */
    public static void uploadObject() {
        // Your object images
        ArrayList<File> images = new ArrayList();
        images.add(new File("/res/drawable/apple/jpg"));
     
        
        // Object infos
        String name = "Computational Geometry, Algorithms and Applications, Third Edition";

        // Optional infos
        String custom_id = "book0001";
        String meta = "{\n\"isbn\": \"9783540779735\"\n}";
        boolean json = true;
        String collection = "books";

        // Upload
        System.out.println("Upload : " + iqe.upload(images, name, custom_id, meta, json, collection));
    }

    private static javaiqe iqe = null;

}
