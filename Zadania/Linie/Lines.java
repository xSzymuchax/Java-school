import java.util.*;

public class Lines implements LinesInterface{
	class Point implements LinesInterface.Point
	{
		private String my_name;
		@Override
		public String getName() {
			return my_name;
		}
		public Point() {
			my_name = "";
		}
	}

	class Segment implements LinesInterface.Segment
	{
		private Point endpoint1;
		private Point endpoint2;
		
		@Override
		public Point getEndpoint1() {
			return endpoint1;
		}

		@Override
		public Point getEndpoint2() {
			return endpoint2;
		}	
	}
	
	public Set<LinesInterface.Point> my_points;
	public Set<LinesInterface.Segment> my_segments;
	Map<LinesInterface.Point, Set<LinesInterface.Segment>> connections_global = new HashMap<>();
	
	@Override
	public void addPoints(Set<LinesInterface.Point> points) {
		my_points = points;		
	}

	@Override
	public void addSegments(Set<LinesInterface.Segment> segments) {
		my_segments = segments;	
	}
	
	
	private String szukaj(LinesInterface.Point start, LinesInterface.Point end, String current_path, int x, String answer)
	{
		if (start.equals(end)) return current_path+start.getName();
		if (current_path.indexOf(start.getName()) != -1) return "";

		current_path = current_path + start.getName();
		
		for (LinesInterface.Segment s : connections_global.get(start))
		{					
			if (s.getEndpoint1().getName().equals(start.getName()) && current_path.indexOf(s.getEndpoint2().getName()) == -1) 
				answer = szukaj(s.getEndpoint2(), end, current_path, 1, answer);			
			else if (s.getEndpoint2().getName().equals(start.getName()) && current_path.indexOf(s.getEndpoint1().getName()) == -1) 
				answer = szukaj(s.getEndpoint1(), end, current_path, 1, answer);			
		}		
		return answer;
	}
	
	@Override
	public List<LinesInterface.Segment> findConnection(LinesInterface.Point start, LinesInterface.Point end) {
		List<LinesInterface.Segment> main_result = new ArrayList<LinesInterface.Segment>();
			
		connections_global = getMapEndpointToSegments();
		
		String result = szukaj(start, end, "", 0, "");
		System.out.println("WYNIK SZUKANIA: " + result);

		if (result.equals("")) return main_result;
		
		for(int i=0;i<result.length()-1;i++) {
			String c1 = Character.toString(result.charAt(i));
			String c2 = Character.toString(result.charAt(i+1));

			for (LinesInterface.Segment s : my_segments)
			{
				if (s.getEndpoint1().getName().equals(c1) && s.getEndpoint2().getName().equals(c2) )
					main_result.add(s);
				
				if (s.getEndpoint1().getName().equals(c2) && s.getEndpoint2().getName().equals(c1))
					main_result.add(s);		
			}
		}	
		return main_result;
	}

	@Override
	public Map<LinesInterface.Point, Set<LinesInterface.Segment>> getMapEndpointToSegments() {		
		
		HashMap<LinesInterface.Point, Set<LinesInterface.Segment>> map = new HashMap<>();	
		for (LinesInterface.Point p : my_points)
		{
			Set<LinesInterface.Segment> paths = new HashSet<LinesInterface.Segment>();
			for (LinesInterface.Segment s : my_segments)
				if (s.getEndpoint1() == p || s.getEndpoint2()==p)
					paths.add(s);
			
			map.put(p, paths);
		}
		return map;
	}
	
	private Set<LinesInterface.Point> szukaj_w_odleglosci(LinesInterface.Point start, int distance, Set<LinesInterface.Point> secik, LinesInterface.Point lastPoint, LinesInterface.Point beginning)
	{	
		if (distance==0)
		{
			if (start==beginning) return secik;
			if (secik.contains(start)) return secik;
			secik.add(start);
			return secik;
		}	
		for (LinesInterface.Segment s : connections_global.get(start))
		{		
			if (s.getEndpoint1()==start && s.getEndpoint2()!=lastPoint) 				
				 secik = szukaj_w_odleglosci(s.getEndpoint2(), distance-1, secik, start, beginning);			
			else if (s.getEndpoint2()==start && s.getEndpoint1()!=lastPoint) 
				secik = szukaj_w_odleglosci(s.getEndpoint1(), distance-1, secik, start, beginning);					
		}	
		return secik;
	}
	
	@Override
	public Map<LinesInterface.Point, Map<Integer, Set<LinesInterface.Point>>> getReachableEndpoints() {		
		connections_global = getMapEndpointToSegments();
		Map<LinesInterface.Point, Map<Integer, Set<LinesInterface.Point>>> result = new HashMap<>();
		
		for (LinesInterface.Point p : my_points) 
		{
			Map<Integer, Set<LinesInterface.Point>> point_result = new HashMap<>();

			for (int i=1;i<=4;i++)
			{
				Set<LinesInterface.Point> set = new HashSet<>();
				set = szukaj_w_odleglosci(p, i, set, p, p);
				point_result.put(i, set);
			}
			result.put(p, point_result);
		}
		return result;
	}

}