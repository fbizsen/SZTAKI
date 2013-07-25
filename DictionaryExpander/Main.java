import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		//Input dic
		String origDicPath = "..\\..\\dic.txt";
		//Input text
		String text = "..\\..\\text.txt";
		//Output dic
		String newDicPath = "..\\..\\ExpandedDic.txt";
		
		try {		
			String currentLine = null;
			BufferedReader br = new BufferedReader(new FileReader(origDicPath));
			BufferedWriter bw = new BufferedWriter(new FileWriter(newDicPath));
			ArrayList<String[]> Dictionary = new ArrayList<String[]>();

			//Beolvassuk a szotarat
			while ((currentLine = br.readLine()) != null) {
				String word[] = new String[2];
				word = currentLine.split(",");
				Dictionary.add(word);
			}
			
			br.close();
			
			//Beolvassuk a szoveget
			br = new BufferedReader(new FileReader(text));
			while ((currentLine = br.readLine()) != null) {
				//Szavakra bontas
				for (int index = 0; index < currentLine.length(); index++) {
					while (index < currentLine.length()
							&& !Character.isJavaIdentifierStart(currentLine
									.charAt(index))
							&& currentLine.charAt(index) != ' ') {
						currentLine = currentLine.replace(
								Character.toString(currentLine.charAt(index)), "");
					}
				}
				//Megvannak a szavak
				String[] lines = currentLine.split(" ");
				
				boolean gotWord = false;
				for(String s : lines){
					s = s.toLowerCase();
					gotWord = false;
					//Megnezzuk hogy a szo benne van-e a szotarban vagy a hossza nagyobb mint 4 betu + \n
					for(String[] dicWord : Dictionary){
						if(s.equals(dicWord[0]) || s.length() > 5){
							gotWord = true;
							break;
						}
					}
					//Ha nincs beirjuk
					if(!gotWord){
						System.out.println("New word: " + s + "," + getPattern(s));
						String[] addable = new String[2];
						addable[0] = s;
						addable[1] = getPattern(s);
						Dictionary.add(addable);
					}
				}
			}
		
		br.close();
		//Rendezes sajat rendezovel
		Collections.sort(Dictionary, new CustomArrayListComparator());
		for(String[] s : Dictionary){
			bw.write(s[0] + "," + s[1]);
			bw.newLine();
		}
		bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String getPattern(String inputWord){
	    ArrayList<String> tempPattern = new ArrayList<String>();
	    int sign = 1;
	    int used = 0;
	    int i;
	    // El��ll�t egy ugyanolyan hossz� list�t, csupa k�rd�jellel
	    for (i = 0; i < inputWord.length(); i++){
	        tempPattern.add("?");
	    }
	        
	    // Ha az input adott karaktere megism�tl�dik, akkor sz�mmal jelzi minden el�fordul�sn�l
	    for (i = 0; i < inputWord.length(); i++){
	        if (tempPattern.get(i) == "?"){
	            //for j in range(i+1,len(word)):
	        	for (int j = i + 1; j < inputWord.length(); j++){
	                if (inputWord.charAt(i) == inputWord.charAt(j)){
	                    if (used==0){
	                        // String indexel�s
	                    	tempPattern.add(i, Integer.toString(sign));
	                    	tempPattern.remove(i+1);
	                    	
	                    }
	                    used=1;
	                    tempPattern.add(j, Integer.toString(sign));
                    	tempPattern.remove(j+1);
	                }
	        	}
	            if( used == 1){
	                if (sign<9){
	                    sign = sign + 1;
	                }	           
	                used=0;
	            }
	        }
	    }
	    // List�b�l string
	    String ret = "";
	    for(String s : tempPattern){
	    	ret += s;
	    }
	    return ret;
	}
}