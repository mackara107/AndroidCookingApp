package com.example.beginnercookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

public class RecipeActivity extends AppCompatActivity {
    private TextView name;
    private TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        name = (TextView) findViewById(R.id.name);
        details = (TextView) findViewById(R.id.details);
    }

    public String parseXML(){
        XmlPullParserFactory parserFactory;
        String text = "";
        try {

            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("recipes.xml");
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
        int count = 0;
        while(count<19){
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
                            count++;
                        }else if("carbs".equals(eltName)){
                            currentElement.carbs = parser.nextText();
                            count++;
                        }else if ("cooktime".equals(eltName)){
                            currentElement.cooktime= parser.nextText();
                            count++;
                        }else if ("name".equals(eltName)){
                            currentElement.name= parser.nextText();
                            count++;
                        }else if("ingredients".equals(eltName)){

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
