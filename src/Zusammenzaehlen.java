public class Zusammenzaehlen implements Runnable
{
    public int initIndex;
    public int finIndex;
    public int localSum;
    public static int globalSum;

    public static int[] a= new int[100000000];

    public static void main(String[] args) throws Exception
    {
        for(int i=0;i<a.length;i++)
        {
            a[i]=1;
        }
        int k=Integer.parseInt(args[0]);
        long startTime=System.nanoTime();
        multithread(k);
        long endTime=System.nanoTime();
        System.out.println("<Versuchsablauf> "+System.getProperty("line.separator")+"Anzahl von Threads: "+args[0] + System.getProperty("line.separator")+"Verlaufene Zeit: "+((endTime-startTime))+"ns"+System.getProperty("line.separator")+"Ergebnis: "+globalSum+System.getProperty("line.separator")+"</Versuchsablauf>");
    }
    public Zusammenzaehlen()
    {
    }
    public void run()
    {

        localSum=sum(a,initIndex,finIndex);
    }
    public static void multithread(int w) throws Exception
    {
        int l=a.length;

        if(w<=l)
        {
            int stepSize=l/w;
            Zusammenzaehlen[] instance = new Zusammenzaehlen[][w];
            Thread[] multiThread= new Thread[w];

            for(int i=0; i<w;i++)
            {
                instance[i]= new Zusammenzaehlen();
                instance[i].initIndex=i*stepSize;
                instance[i].finIndex=i*stepSize-1+stepSize;
                if(i==w-1)
                {
                    instance[i].finIndex=l-1;
                }
                multiThread[i]= new Thread(instance[i]);
                multiThread[i].start();
            }

            for(int j=0; j<w;j++)
            {

                multiThread[j].join();
                globalSum+=instance[j].localSum;
            }

        }
        else
        {
            System.err.println("ACHTUNG: Bitte geben Sie eine niedrigere Zahl ein "+l);
        }

    }
    public static int sum(int[] m, int initIndex, int finIndex)
    {
        int sum=0;

        for(int i=initIndex;i<=finIndex;i++)
        {
            sum+=m[i];
        }
        return sum;
    }

}