package problem;
import org.jacop.core.BooleanVar;
import org.jacop.core.Store;
import org.jacop.jasat.utils.structures.IntVec;
import org.jacop.satwrapper.SatWrapper;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SmallestDomain;

public class Solver {

	public static void main(String[] args) {
		Store store = new Store();
		SatWrapper satWrapper = new SatWrapper(); 
		store.impose(satWrapper);					/* Importante: sat problem */


		/* Creating the literals */
		BooleanVar a = new BooleanVar(store, "Pink Salmon in tank 1");
		BooleanVar b = new BooleanVar(store, "Pink Salmon in tank 2");
		BooleanVar c = new BooleanVar(store, "Pink Salmon in tank 3");
		BooleanVar d = new BooleanVar(store, "Carp in tank 1");
		BooleanVar e = new BooleanVar(store, "Carp in tank 2");
		BooleanVar f = new BooleanVar(store, "Carp in tank 3");
		BooleanVar g = new BooleanVar(store, "Grouper in tank 1");
		BooleanVar h = new BooleanVar(store, "Grouper in tank 2");
		BooleanVar i = new BooleanVar(store, "Grouper in tank 3");
		BooleanVar j = new BooleanVar(store, "Gilt-head in tank 1");
		BooleanVar k = new BooleanVar(store, "Gilt-head in tank 2");
		BooleanVar l = new BooleanVar(store, "Gilt-head in tank 3");


		BooleanVar[] allVariables = new BooleanVar[]{a,b,c,d,e,f,g,h,i,j,k,l};


		/* Registering the literals in the sat wrapper */
		satWrapper.register(a);
		satWrapper.register(b);
		satWrapper.register(c);
		satWrapper.register(d);
		satWrapper.register(e);
		satWrapper.register(f);
		satWrapper.register(g);
		satWrapper.register(h);
		satWrapper.register(i);
		satWrapper.register(j);
		satWrapper.register(k);
		satWrapper.register(l);


		
		int aLiteral = satWrapper.cpVarToBoolVar(a, 1, true);
		int bLiteral = satWrapper.cpVarToBoolVar(b, 1, true);
		int cLiteral = satWrapper.cpVarToBoolVar(c, 1, true);
		int dLiteral = satWrapper.cpVarToBoolVar(d, 1, true);
		int eLiteral = satWrapper.cpVarToBoolVar(e, 1, true);
		int fLiteral = satWrapper.cpVarToBoolVar(f, 1, true);
		int gLiteral = satWrapper.cpVarToBoolVar(g, 1, true);
		int hLiteral = satWrapper.cpVarToBoolVar(h, 1, true);
		int iLiteral = satWrapper.cpVarToBoolVar(i, 1, true);
		int jLiteral = satWrapper.cpVarToBoolVar(j, 1, true);
		int kLiteral = satWrapper.cpVarToBoolVar(k, 1, true);
		int lLiteral = satWrapper.cpVarToBoolVar(l, 1, true);


		/* Clauses */
		/* Incompatible fishes */
		addClause(satWrapper, -gLiteral, -aLiteral);		/* (-g v -a) */
		addClause(satWrapper, -gLiteral, -dLiteral);		/* (-g v -d) */
		addClause(satWrapper, -hLiteral, -bLiteral);		/* (-h v -b) */
		addClause(satWrapper, -hLiteral, -eLiteral);		/* (-h v -e) */
		addClause(satWrapper, -iLiteral, -cLiteral);		/* (-i v -c) */
		addClause(satWrapper, -iLiteral, -fLiteral);		/* (-i v -f) */
		addClause(satWrapper, -jLiteral);					/* (-j) */
		addClause(satWrapper, -cLiteral);					/* (-c) */
		/* At least a fish on each tank */	
		addClause(satWrapper, aLiteral, bLiteral, cLiteral);		/* (a v b v c) */
		addClause(satWrapper, dLiteral, eLiteral, fLiteral);		/* (d v e v f) */
		addClause(satWrapper, gLiteral, hLiteral, iLiteral);		/* (g v h v i) */
		addClause(satWrapper, jLiteral, kLiteral, lLiteral);		/* (j v k v l) */
		/* Clauses that force a maximum of 2 fishes per tank */
		addClause(satWrapper, -aLiteral, -dLiteral, -gLiteral);		/* (-a v -d v -g) */
		addClause(satWrapper, -aLiteral, -gLiteral, -jLiteral);		/* (-a v -g v -j) */
		addClause(satWrapper, -aLiteral, -dLiteral, -jLiteral);		/* (-a v -d v -j) */
		addClause(satWrapper, -dLiteral, -gLiteral, -jLiteral);
		
		addClause(satWrapper, -bLiteral, -eLiteral, -hLiteral);		/* (-b v -e v -h) */
		addClause(satWrapper, -bLiteral, -hLiteral, -kLiteral);		/* (-b v -h v -k) */
		addClause(satWrapper, -bLiteral, -eLiteral, -kLiteral);		/* (-b v -e v -k) */
		addClause(satWrapper, -eLiteral, -hLiteral, -kLiteral);		/* (-e v -h v -k) */
		
		addClause(satWrapper, -cLiteral, -fLiteral, -iLiteral);		/* (-c v -f v -i) */
		addClause(satWrapper, -cLiteral, -fLiteral, -lLiteral);		/* (-c v -f v -l) */
		addClause(satWrapper, -cLiteral, -iLiteral, -lLiteral);		/* (-c v -i v -l) */
		addClause(satWrapper, -fLiteral, -iLiteral, -lLiteral);		/* (-f v -i v -l) */
		/* Solving the problem */
	    Search<BooleanVar> search = new DepthFirstSearch<BooleanVar>();
		SelectChoicePoint<BooleanVar> select = new SimpleSelect<BooleanVar>(allVariables,
							 new SmallestDomain<BooleanVar>(), new IndomainMin<BooleanVar>());
		Boolean result = search.labeling(store, select);

		if (result) {
			System.out.println("Solution: ");

			if(a.dom().value() == 1){
				System.out.println(a.id());
			}

			if(b.dom().value() == 1){
				System.out.println(b.id());
			}

			if(c.dom().value() == 1){
				System.out.println(c.id());
			}

			if(d.dom().value() == 1){
				System.out.println(d.id());
			}
			
			if(e.dom().value() == 1){
				System.out.println(e.id());
			}
			
			if(f.dom().value() == 1){
				System.out.println(f.id());
			}
			
			if(g.dom().value() == 1){
				System.out.println(g.id());
			}
			
			if(h.dom().value() == 1){
				System.out.println(h.id());
			}
			
			if(i.dom().value() == 1){
				System.out.println(i.id());
			}
			
			if(j.dom().value() == 1){
				System.out.println(j.id());
			}
			
			if(k.dom().value() == 1){
				System.out.println(k.id());
			}
			
			if(l.dom().value() == 1){
				System.out.println(l.id());
			}

		} else{
			System.out.println("*** No");
		}

		System.out.println();
	}

	public static void addClause(SatWrapper satWrapper, int literal1){
		IntVec clause = new IntVec(satWrapper.pool);
		clause.add(literal1);
		satWrapper.addModelClause(clause.toArray());
	}
	
	
	public static void addClause(SatWrapper satWrapper, int literal1, int literal2){
		IntVec clause = new IntVec(satWrapper.pool);
		clause.add(literal1);
		clause.add(literal2);
		satWrapper.addModelClause(clause.toArray());
	}


	public static void addClause(SatWrapper satWrapper, int literal1, int literal2, int literal3){
		IntVec clause = new IntVec(satWrapper.pool);
		clause.add(literal1);
		clause.add(literal2);
		clause.add(literal3);
		satWrapper.addModelClause(clause.toArray());
	}
}
