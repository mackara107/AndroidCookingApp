package com.example.beginnercookingapp;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Parser extends AppCompatActivity {
    public String parseXML(InputStream is){
        XmlPullParserFactory parserFactory;
        String text = "";
        try {

            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is,null);
            text = processParsing(parser);


        }catch(XmlPullParserException e){

        }catch(IOException e){

        }
        return text;
    }
    private String processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<Recipe> element= new ArrayList<>();
        int eventType= parser.getEventType();
        Recipe currentElement = null;
        while(eventType != XmlPullParser.END_DOCUMENT){
            String eltName = null;
            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName= parser.getName();
                    if("recipe".equals(eltName)){
                        currentElement = new Recipe();
                        element.add(currentElement);
                    }else if(currentElement!= null){
                        if("calories".equals(eltName)){
                            currentElement.calories = parser.nextText();
                        }else if("carbs".equals(eltName)){
                            currentElement.carbs = parser.nextText();
                        }else if ("cooktime".equals(eltName)){
                            currentElement.cooktime= parser.nextText();
                        }else if ("name".equals(eltName)){
                            currentElement.name= parser.nextText();
                        }
                    }
                    break;

            }
            eventType= parser.next();

        }
        String text = printElements(element);
        return text;

    }
    private String printElements(ArrayList<Recipe> root){
        StringBuilder builder = new StringBuilder();
        for(Recipe element : root){
            builder.append("\n").append(element.name).append("\nCalories: ").append(element.calories).append("\nCarbs: ").append(element.carbs).append("\nTime: ").append(element.cooktime).append("\n");
        }
        return(builder.toString());
    }
}
