import java.util.*;

public class Meeting implements MeetingInterface{

	public ArrayList<PawnPosition> myPawns;
	public Position meetingPoint;
	
	@Override
	public void addPawns(List<PawnPosition> positions) {
		myPawns = new ArrayList<PawnPosition>(positions);
		
	}

	@Override
	public void addMeetingPoint(Position meetingPointPosition) {
		meetingPoint = meetingPointPosition;		
	}
	
	//Funkcja odpowiadajaca za ruch po wspolrzednej x
	private boolean moveX(PawnPosition p, int dx)
	{
		if (dx==0) return false;
		
		Set<PawnPosition> neigh = new HashSet<>(getNeighbours(p.pawnId()));
		boolean free_tile = true;
		
		int way =0;	
		if (dx<0) way = -1;
		else way = 1;
		
		for (PawnPosition n : neigh) //sprawdz czy zaden sasiad nie stoi na polu
		{
			if (n.x() == p.x()+way && n.y()==p.y())
			{
				free_tile = false;
				break;
			}
		}
		
		if(free_tile) //jesli pole jest wolne postaw pionka na tym polu
		{
			PawnPosition new_pawn = new PawnPosition2D(p.pawnId(), p.x()+way, p.y());
			myPawns.set(myPawns.indexOf(p), new_pawn);
			return true;
		}
		
		return false;
	}
	
	//Funkcja odpowiadajaca za ruch po wspolrzednej y
		private boolean moveY(PawnPosition p, int dy)
		{
			if (dy==0) return false;
			
			Set<PawnPosition> neigh = new HashSet<>(getNeighbours(p.pawnId()));
			boolean free_tile = true;
			
			int way =0;	
			if (dy<0) way = -1;
			else way = 1;
			
			for (PawnPosition n : neigh) //sprawdz czy zaden sasiad nie stoi na polu
			{
				if (n.y() == p.y()+way && n.x() == p.x())
				{
					free_tile = false;
					break;
				}
			}
			
			if(free_tile) //jesli pole jest wolne postaw pionka na tym polu
			{
				PawnPosition new_pawn = new PawnPosition2D(p.pawnId(), p.x(), p.y()+way);
				myPawns.set(myPawns.indexOf(p), new_pawn);
				
				return true;
			}
			
			return false;
		}
	
	@Override
	public void move() {
		boolean any_moved = true;
		boolean moved = false;
		int round = 1;
		
		int dx, dy;
		PawnPosition current_pawn;
		while (any_moved)
		{
			any_moved = false;
			if (round%2 == 1)
			{
				for (int i=0;i<myPawns.size();i++)
				{
					current_pawn  = myPawns.get(i);
					dx = meetingPoint.x() - current_pawn.x();
					dy = meetingPoint.y() - current_pawn.y();
			 
					if (dx==dy && dx==0) 
					{
						moved = false;
						continue;
					}
					
					if (Math.abs(dx) > Math.abs(dy))
					{
						moved = moveX(current_pawn, dx);
					}
					else
					{
						moved = moveY(current_pawn, dy);
					}
					
					if (moved == true) any_moved = true;
				}
			}
			else
			{
				for (int i=myPawns.size()-1;i>=0;i--)
				{
					current_pawn  = myPawns.get(i);
					dx = meetingPoint.x() - current_pawn.x();
					dy = meetingPoint.y() - current_pawn.y();
					
					if (dx==dy && dx==0) 
					{
						moved = false;
						continue;
					}
					
					if (Math.abs(dx) > Math.abs(dy))
					{
						moved = moveX(current_pawn, dx);
					}
					else if (Math.abs(dx) <= Math.abs(dy))
					{
						moved = moveY(current_pawn, dy);
					}
					
					if (moved == true) any_moved = true;
				}
			}
			round++;
		}	
	}

	@Override
	public Set<PawnPosition> getAllPawns() {
		Set<PawnPosition> finalPos = new HashSet<>(myPawns);		
		return finalPos;
	}

	private PawnPosition findPawnOfID(int pawnId)
	{
		for (PawnPosition p : myPawns) 
		{
			if (p.pawnId() == pawnId) return p;
		}
		return null;
	}
	
	@Override
	public Set<PawnPosition> getNeighbours(int pawnId) {
		Set<PawnPosition> neighbours = new HashSet<>();
				
		PawnPosition myPawn = findPawnOfID(pawnId);
		
		int x = myPawn.x();
		int y = myPawn.y();
		
		for (PawnPosition pawn : myPawns) 
		{
			if (pawn.x() == x && pawn.y() == y+1) neighbours.add(pawn);
			else if (pawn.x() == x+1 && pawn.y() == y+1) neighbours.add(pawn);
			else if (pawn.x() == x+1 && pawn.y() == y) neighbours.add(pawn);
			else if (pawn.x() == x+1 && pawn.y() == y-1) neighbours.add(pawn);
			else if (pawn.x() == x && pawn.y() == y-1) neighbours.add(pawn);
			else if (pawn.x() == x-1 && pawn.y() == y-1) neighbours.add(pawn);
			else if (pawn.x() == x-1 && pawn.y() == y) neighbours.add(pawn);
			else if (pawn.x() == x-1 && pawn.y() == y+1) neighbours.add(pawn);
		}
		
		
		return neighbours;
	}

	
}
