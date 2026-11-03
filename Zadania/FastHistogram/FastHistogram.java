import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FastHistogram implements Histogram{
	Map<Integer, Integer> my_histogram = new HashMap<>();
	boolean isReadyInfo = false;
	int threadsCount;
	int binsCount;
	Vector3 myVector;
	public int vecSize = 0;
	public int vecPos = 0;
	public int ready=0;
	private final AtomicBoolean locked = new AtomicBoolean(false);
	
	private boolean lockSuccesful() {
		return locked.compareAndSet(false, true);
	}
	
	public void lock() {
        while( ! lockSuccesful() ) {
            synchronized( locked ) {
                
            }
        }
    }
	
	public void unlock() {
		locked.set( false );
	}
	
	@Override
	public void setup(int threads, int bins) {
		my_histogram = new HashMap<>();
		threadsCount = threads;
		binsCount = bins;	
		//System.out.println("tred kount: " + threadsCount);
		/*for (int i=0;i<bins;i++) {
			my_histogram.put(i, 0);
		}*/
	}

	public void createHistogram() {
		ready=0;
		//my_histogram.put(myVector.getValue(vecSize-1), my_histogram.get(myVector.getValue(vecSize-1)) + 1);
		//System.out.println("tred kount: " + threadsCount);
		int part = vecSize / threadsCount;
		
		int[] bounds = new int[threadsCount];
		for (int i=0;i<threadsCount;i++) {
			bounds[i] = i*part;
		}
		
		new Thread() {
			public void run() 
			{
				if (bounds[threadsCount-1] <= vecSize-1) 
				{
					int[] values = new int[binsCount];
					int placeIndex=0;
					int my_index = bounds[threadsCount-1];
					
					while (my_index != vecSize) {
						placeIndex = myVector.getValue(my_index);
						values[placeIndex]++;
						my_index++;
					}
				
					
				lock();				
				for (int j=0;j<binsCount;j++) {
					if (values[j] == 0) continue;
					if (my_histogram.get(j)!=null)
						my_histogram.put(j, my_histogram.get(j) + values[j]); 
					else
						my_histogram.put(j, values[j]); 
				}
				ready++;
				unlock();
				}
			}
		}.start();
		
			
		
		for (int i=0;i<threadsCount-1;i++) {
			int my_start = bounds[i];
			int my_end = bounds[i+1];
			
			System.out.println("my_start: " + my_start + " my_end: " + my_end);
			
			new Thread() {
				@Override
				public void run() {
					int[] values = new int[binsCount];
					int placeIndex=0;
					int my_index = my_start;
					
					while (my_index != my_end) {
						placeIndex = myVector.getValue(my_index);
						values[placeIndex]++;
						my_index++;
					}
					
					lock();				
					for (int j=0;j<binsCount;j++) {
						if (values[j] == 0) continue;
						if (my_histogram.get(j)!=null)
							my_histogram.put(j, my_histogram.get(j) + values[j]); 
						else
							my_histogram.put(j, values[j]); 
					}
					ready++;
					unlock();
				}
			}.start();
			
		}
		
		
	}
	
	
	@Override
	public void setVector(Vector3 vector) {
		isReadyInfo = false;
		myVector = vector;
		vecSize = vector.getSize();
		vecPos = 0;
		
		createHistogram();
	}
	
//a
	@Override
	public boolean isReady() {
		return ready==threadsCount;
	}
	

	@Override
	public Map<Integer, Integer> histogram() {
		if (isReady() == true) return my_histogram;
		return null;
	}
	
	/*public static void main(String[] args) throws InterruptedException{
		FastHistogram fs = new FastHistogram();
		final Vector3 vec = new Vector3();
		
		fs.setup(3, 7);
		
		vec.generate(29000);
		fs.setVector(vec);
		
		//fs.createHistogram();
		Thread.sleep(3000);
		
		for(int i=0;i<1;i++)
			System.out.println(fs.my_histogram);
	}*/
}
