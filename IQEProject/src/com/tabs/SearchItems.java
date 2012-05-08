package com.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
/**
 * @author Katarzyna Koryzma
 * random number generation method with the help of stackoverflow.com
 */
public class SearchItems extends Activity {
static ArrayList<Integer> ints;
static int rand,maxRandomNumber;
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
       
}public static int[] getRandomArray( int maxRandomNumber){
    int randomCount =10;
    if(randomCount >maxRandomNumber ){
        return null;
    }
    ArrayList<Integer> arrayList = new ArrayList<Integer>();
    for(int i=0;i<maxRandomNumber;i++){
        arrayList.add(i);
    }
    int[] arr = new int[randomCount ];
    for(int i=0;i<randomCount;i++){
        int random = new Random().nextInt(arrayList.size());
        arr[i]=arrayList.remove(random);        
    }
    return arr;
}

public static ArrayList<String> LevelOneTreasures() {
	
	ArrayList<String> getRandomItems = new ArrayList<String> ();
	String[] levelOne = {"cat", "apple" , "Sunglasses",
			"Coin","Yellow Flower",
			"Orange",	"Red Berry", "Lighter","Galaxy Cake Bar",
			"Squash"	,"Carrot"	,"Apple","Wine Glass",
			"Pumpkin", "Bean",	"Corn",
			"Yellow Candle"	,"Nail Polish","Jellybean",
			"Red Lipstick", 
			" Egg",	"Mustard",	"Yellow Crayon",
			"Balloon","ELLE Magazine",	"Cinnamon Stick",
			 "Currency Note ",
			"A post card from London" ,
			"Zippo lighter",
			"Starbucks Coffee Cup",
			"Costa Coffee ","Star Bar","Snickers","Kit Kat",
			"Mars Bar",
			"Toblerone chocolate",
			"Lindt chocolate",
			"Wonka Bar","Cadbury's Fruit & Nut Tasters",
			"Cadbury's Whole Nut",
			"Cadbury's Whole Nut Tasters",	
			"Cadbury's Bournville",
			"Cadbury's Orange"};
			
			
	
	ArrayList<String> list = new ArrayList<String>(levelOne.length);  
	for (String s : levelOne) {  
	    list.add(s);  
	} 
	double tot= (double) list.size()-1; int tenN=0;
	ArrayList<String> ReturnList = new ArrayList<String>();
    
	int[] rands= getRandomArray(30);
	
	for (int y=0; y<10; y++) {
		
        ReturnList.add(list.get(rands[y]));
	}
		
	return ReturnList;
}

public static ArrayList<String> LevelTwoTreasures(){
	
	ArrayList<String> getRandomItems = new ArrayList<String> ();
	String[] levelTwo = { "Carling",
			
			 "pebble",
			"Worm",
			"Caterpillar",
			"Squirrel",
			"Bird",
			"Ant",
			"Snail",
			"Ladybug",
			"Frog", "Red Lipstick", 
			" Egg",	"Mustard",	"Yellow Crayon",
			"Balloon","ELLE Magazine",	"Cinnamon Stick","hula-hoop ","Star Bar","Snickers","Kit Kat",
			"Mars Bar",
			"Toblerone chocolate",
			"Lindt chocolate","Headphones", "Mouse", "Key"};

	ArrayList<String> list = new ArrayList<String>(levelTwo.length);  
	for (String s : levelTwo) {  
	    list.add(s);  
	} 
	double tot= (double) list.size()-1;
	ArrayList<String> ReturnList = new ArrayList<String>();
	
	Random rng = new Random(); // Ideally just create one instance globally
	List<Integer> generated = new ArrayList<Integer>();
int[] rands= getRandomArray(16);
	
	for (int y=0; y<10; y++) {
		
        ReturnList.add(list.get(rands[y]));
	}

	return ReturnList;
}

public static ArrayList<String> LevelThreeTreasures(){
	ArrayList<String> levelThree = new ArrayList<String> ();
 levelThree.add("Porshe car");
 levelThree.add("Rice Milk");levelThree.add("The Da Vinci Code");levelThree.add("Rioja Reserva");levelThree.add( "pink bottle");levelThree.add("paperclip");
 levelThree.add("Dream of the Red Chamber book");
 levelThree.add( "Spider web");levelThree.add("butterfly");levelThree.add( "lake");levelThree.add("Peach cake");levelThree.add("Birds nest");
		double tot= (double) levelThree.size()-1;
		
		ArrayList<String> list = new ArrayList<String>(levelThree.size());  
		for (String s : levelThree) {  
		    list.add(s);  
		} 

	ArrayList<String> ReturnList = new ArrayList<String>();
	Random rng = new Random(); // Ideally just create one instance globally
	List<Integer> generated = new ArrayList<Integer>();
int[] rands= getRandomArray(10);
	
	for (int y=0; y<10; y++) {
		
        ReturnList.add(list.get(rands[y]));
	}
	
	return ReturnList;
	
}

public static ArrayList<String> LevelFourTreasures(){
	ArrayList<String> getRandomItems = new ArrayList<String> ();

String[] levelFour=  {"food processor", "Aston Martin", "LCD Screen", "Big Ben", "Swiss Cottage underground station", "Rolex watch", "mouse", 
		"yellow hat", "Moet & Chandon Champagne", "red suitcase", "tennis ball" };

ArrayList<String> list = new ArrayList<String>(levelFour.length);  
for (String s : levelFour) {  
    list.add(s);  
} 
double tot= (double) list.size()-1;
ArrayList<String> ReturnList = new ArrayList<String>();

Random rng = new Random(); // Ideally just create one instance globally
List<Integer> generated = new ArrayList<Integer>();

int[] rands= getRandomArray(11);

for (int y=0; y<10; y++) {
	
    ReturnList.add(list.get(rands[y]));
}
return ReturnList;
	
}
}
