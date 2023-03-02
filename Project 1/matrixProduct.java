import java.lang.Math;
import java.util.*;

class matrixProduct {

    public static final int SIZE_OF_DOUBLE = 8;
    
    static void OnMult(int m_ar, int m_br) {
        double time1, time2, temp;
        int i, j, k;

        double[] pha, phb, phc;

        pha = new double[(m_ar * m_br)];
        phb = new double[(m_ar * m_br)];
        phc = new double[(m_ar * m_br)];
        
        for(i = 0; i < m_ar; i++) {
            for(j=0; j < m_ar; j++) {
                pha[i * m_ar + j] = (double)1.0;
            }
        }

        for(i = 0; i < m_br; i++) {
            for(j=0; j < m_br; j++) {
			    phb[i * m_br + j] = (double)i+1;
            }
        }

        time1 = System.nanoTime();

        for(i = 0; i < m_ar; i++) {
            for(j=0; j < m_br; j++) {
                temp = 0;
                for(k=0; k < m_ar; k++) {
                    temp += pha[i * m_ar + k] * phb[k * m_br + j];
                }
                phc[i * m_ar + j] = temp;
            }
        }

        time2 = System.nanoTime();

        System.out.printf("Time: %3.3f" + "seconds\n", (time2-time1)/(Math.pow(10, 9)));
        System.out.println("Result matrix:");

        for(i = 0; i < 1; i++) {
            for(j = 0; j < Math.min(10,m_br); j++) {
                System.out.print(phc[j] + " ");
            }
        }
    }

    static void OnMultLine(int m_ar, int m_br) {

        double time1, time2;
        int i, j, k;

        double[] pha, phb, phc;

        pha = new double[(m_ar * m_br)];
        phb = new double[(m_ar * m_br)];
        phc = new double[(m_ar * m_br)];
        
        for(i = 0; i < m_ar; i++) {
            for(j=0; j < m_ar; j++)
			    pha[i * m_ar + j] = (double)1.0;
        }

        for(i = 0; i < m_br; i++) {
            for(j=0; j < m_br; j++)
			    pha[i * m_br + j] = (double)i+1;
        }

        time1 = System.nanoTime();

        for(i=0; i<m_ar; i++) {
            for(k=0; k<m_ar; k++) {
                for(j=0; j<m_br; j++) {
                    phc[i*m_ar+j] += pha[i*m_ar+k] * phb[k*m_br+j];
                }
            }
        }

        time2 = System.nanoTime();

        System.out.printf("Time: %3.3f" + "seconds\n", (time2-time1)/(Math.pow(10, 9)));
        System.out.println("Result matrix:");

        for(i = 0; i < 1; i++) {
            for(j = 0; j < Math.min(10,m_br); j++) {
                System.out.println(phc[j]);
            }
        }

    }

    public static void main(String[] args) {
        int lin, col, blockSize;
        int op;

        Scanner sc = new Scanner(System.in);

        op = 1;
        do {
            System.out.println("1. Multiplication");
            System.out.println("2. Line Multiplication");
            System.out.println("3. Block Multiplication");
            System.out.println("0. Exit");
            System.out.print("Selection: ");
            op = sc.nextInt();
            System.out.println("");
            if (op == 0)
                break;
            System.out.print("Dimensions: lins=cols ? ");
            lin = sc.nextInt();
            col = lin;
            System.out.println("");
    
            switch (op){
                case 1: 
                    OnMult(lin, col);
                    break;
                case 2:
                    OnMultLine(lin, col);  
                    break;
                default:
                    op=1;
                    break;
            }    
    
        } while (op != 0);

        sc.close();
    }
}