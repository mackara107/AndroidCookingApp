package com.example.beginnercookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class ProfileActivity extends AppCompatActivity {
 private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //txt =(TextView) findViewById(R.id.txt);
       // parseXML();


    }
private void parseXML(){
    XmlPullParserFactory parserFactory;
    try {

        parserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserFactory.newPullParser();
         InputStream is = getAssets().open("recipes.xml") ;
parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
parser.setInput(is,null);
processParsing(parser);


    }catch(XmlPullParserException e){

    }catch(IOException e){

    }
}
private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<Recipe> element= new ArrayList<>();
        int eventType= parser.getEventType();
        Recipe currentElement = null;
        while(eventType != XmlPullParser.END_DOCUMENT){
            String eltName = null;
            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName= parser.getName();
                    if("element".equals(eltName)){
                        currentElement = new Recipe();
                        element.add(currentElement);
                    }else if(currentElement!= null){
                        if("calories".equals(eltName)){
                            currentElement.calories = parser.nextText();
                        }else if("carbs".equals(eltName)){
                            currentElement.carbs = parser.nextText();
                        }else if ("cooktime".equals(eltName)){
                            currentElement.cooktime= parser.nextText();
                        }
                    }
                    break;

            }
            eventType= parser.next();

        }
        printElements(element);

}
private void printElements(ArrayList<Recipe> root){
        StringBuilder builder = new StringBuilder();
        for(Recipe element : root){
            builder.append(element.calories).append(element.carbs).append("\n").append(element.cooktime).append("\n");
        }
        txt.setText(builder.toString());
}

}

