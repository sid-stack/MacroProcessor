/*
 * MacroProcessor: MNT and MDT (Macros with param)
 * @author Sid-Stack
 * @version 1.2
 */
import java.util.*;
import java.io.*;
public class macroswithparam
{
    static Set<String> mnt = new LinkedHashSet<>();
    static List<String> mdt = new ArrayList<>();
    static List<Integer> macro_ptr = new ArrayList<>(); 
    static List<Integer> noParam = new ArrayList<>(); 
    static HashMap<String, String > actualArgs = new HashMap<>();
    static HashMap<String, String > formalArgs = new HashMap<>();
    static String fname ;
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
            System.out.println("No.\tName\tParam\tLC");
            int k = 0;
            for(String item:mnt)
            {            
                ++i;
                System.out.println(i+"\t"+item+"\t"+noParam.get(k)+"\t"+macro_ptr.get(k));            
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

    static void ALA()
    {
        int flag = 0, c = 0;
        try{
            File f = new File(fname);
            Scanner file = new Scanner(f);
            while(file.hasNextLine())
            {
                String data = file.nextLine().trim();
                if(data.contains("START"))
                {
                    flag = 1; 
                }
                else if(flag == 1)
                {                    
                    String macro_call = data.split(" ")[0];                               
                    if(mnt.contains(macro_call))
                    {
                        String act_parameter = data.substring(data.indexOf(" "));
                        String formal_parameter = formalArgs.get(macro_call);

                        if(act_parameter.split(",").length == formal_parameter.split(",").length)
                        {
                            actualArgs.put(macro_call,act_parameter);   
                        }   
                        else
                        {
                            System.out.println("Wrong number of parameters entered");
                            System.exit(0);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("File:");
        fname = sc.nextLine();
        System.out.println();
        try{
            File f = new File(fname);
            Scanner file = new Scanner(f);
            int flag=0, line=0, lc=0;
            macro_ptr.add(1);

            while(file.hasNextLine())
            {
                String data = file.nextLine().strip(); 
                if(data.contains("MACRO"))
                {
                    String name = data.split(" ")[1];
                    macroName(name);
                    String formal_parameter = data.substring(data.indexOf(" ",6)+1);
                    noParam.add(formal_parameter.split(",").length);
                    formalArgs.put(name ,formal_parameter);
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
        ALA();
        System.out.println("PARAMETER LIST:");
        System.out.println("MACRO\tFORMAL --> ACTUAL");
        //System.out.println();
        for(String k: formalArgs.keySet())
        {
            System.out.println(k.toUpperCase());
            System.out.println(formalArgs.get(k)+" --> "+actualArgs.get(k));
            System.out.println();
        }

    }
}

