import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

class Vector3 implements Vector2{
	Vector<Integer> vec = new Vector<>();
	
	public void generate(int size, int maxValue) {
		vec.clear();
		for (int i=0;i<size;i++)
			vec.add(ThreadLocalRandom.current().nextInt(0, maxValue));
		System.out.println(vec);
	}
	
	@Override
	public int getValue(int idx) {
		return vec.get(idx);
	}

	@Override
	public int getSize() {
		return vec.size();
	}

}


public class MultipleHistograms implements Histogram {
	Map<Integer, Vector3> my_vectors = new HashMap<>();
	Consumer<Histogram.HistogramResult> my_consumer;
	int my_bins=0;
	
	@Override
	public void setup(int bins, Consumer<Histogram.HistogramResult> histogramConsumer) {
		my_bins = bins;
		my_consumer = histogramConsumer;
	}

	private void histogramReturner(Histogram.HistogramResult hr) {
		new Thread() {
			@Override
			public void run() {
				my_consumer.accept(hr);
			}
		}.start();
	}
	
	private void createHistogram(int vectorID, Vector3 vector) {
		Histogram.HistogramResult hr = null;
		Consumer<Histogram.HistogramResult> daj = dane -> histogramReturner(dane);
		
		new Thread() {
			public void run() {
				Map<Integer, Integer> newHistogram = new HashMap<>();
				
				int[] helper = new int[my_bins];
				for (int i=0;i<my_bins;i++)
					helper[i] = 0;
				
				for (int i=0;i<vector.getSize();i++) {
					helper[vector.getValue(i)]++;
				}
				
				for(int i=0;i<my_bins;i++) {
					if (helper[i]==0) continue;
					newHistogram.put(i, helper[i]);
				}
				daj.accept(new Histogram.HistogramResult(vectorID, newHistogram));
			};
		}.start();	
		
	}
	
	@Override
	public void addVector(int vectorID, Vector3 vector) {
		my_vectors.put(vectorID, vector);
		createHistogram(vectorID, vector);
	}

	public static void main(String[] args) {
		Vector3 vec = new Vector3();
		MultipleHistograms mulHis = new MultipleHistograms();
		Consumer<Histogram.HistogramResult> cons = jajo -> System.out.println(jajo);
		vec.generate(10, 5);
		//System.out.println(vec);
		mulHis.setup(5, cons);
		
		vec.generate(10, 5);
		mulHis.addVector(0, vec);
		
		vec.generate(10, 5);
		mulHis.addVector(1, vec);
	}
}
