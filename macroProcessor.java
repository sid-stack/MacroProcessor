/* MacroProcessor: MNT and MDT (Macros without param)
 * @author Sid-Stack
 * @version 1.1 
 */
import java.util.*;
import java.io.*;
public class macroProcessor
{
    static Set<String> mnt = new LinkedHashSet<>();
    static List<String> mdt = new ArrayList<>();
    static List<Integer> macro_ptr = new ArrayList<>();    
    static int cnt=0;   
    
    static void macroName(String name)
    {
        mnt.add(name);        
        cnt++;
    }
    static void macroData(String data)
    {
        mdt.add(data);        
    }
    static void printTable(String n)
    {
        int i = 0;
        if(n.equalsIgnoreCase("mnt"))
        {
            System.out.println("MNT:");
            System.out.println("No.\tName\tLC");
            int k = 0;
            for(String item:mnt)
            {            
                ++i;
                System.out.println(i+"\t"+item+"\t"+macro_ptr.get(k));   //                
                k++;
            }
            System.out.println();
        }
        else if(n.equalsIgnoreCase("mdt"))
        {        
            System.out.println("MDT:");
            System.out.println("LC\tDATA");            
            for(String d:mdt)
            {
                ++i;
                System.out.println(i+"\t"+d);
            }
            System.out.println();
        }
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("File:"); 
        String fname = sc.nextLine();
        System.out.println();
        try{
            File f = new File(fname);
            Scanner file = new Scanner(f);
            int flag=0, line=0, lc=0;
            macro_ptr.add(1);
            while(file.hasNextLine())
            {
                String data = file.nextLine(); 
                if(data.contains("MACRO"))
                {
                    String name = data.split(" ")[1];
                    macroName(name);
                    flag = 1;
                }
                else if(flag==1)
                {
                    lc++;
                    if(data.equals("MEND"))
                    {
                        line++;
                        macroData(data);
                        macro_ptr.add(lc+1);
                        flag = 0;
                    }                    
                    else
                    {
                        macroData(data);
                        line++;
                    }
                }
                else{
                    continue;
                }
            }
            macro_ptr.remove(macro_ptr.size()-1);
        }    
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        printTable("MNT");
        printTable("MDT");
    }
}
