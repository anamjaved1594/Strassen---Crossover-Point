import java.util.*;
import java.io.*;
import java.io.File;


public class strassen
{
    
    // check if is power of 2
     public static boolean isPowerOfTwo(int x)
      {
          return (x & (x - 1)) == 0;
      }
      
    // finding the next highest power of 2 for padding
        public static int nextPowerOf2(final int a)
    {
        int b = 1;
        while (b < a)
        {
            b = b << 1;
        }
        return b;
    }
        
        
        
           // implement simple matrix multiplication
    
      public static int[] simple_multiplication(int matrix_one[], int matrix_two[],int matrix_final[], int dimension)
      {
          for (int i = 0; i < dimension; i ++)
          {
              for (int j = 0; j < dimension; j++)
              {
                  matrix_final[i*dimension+j] = 0;
                  
                  for (int k = 0; k < dimension; k++)
                  {
                      matrix_final[i*dimension+j] += matrix_one[i*dimension+k]*matrix_two[k*dimension+j];
                  }
              }
          }
          return matrix_final;
          
      }
      
      
      /*
      helper functions to make Strassen's work
      adding 2 matrices
      splitting & joining 2 matrices
      subtracting 2 matrices
      */
      
      public static int[] add(int[]mat_one, int[]mat_two)
      {
          
          // get size of input matrices to know result of final matrix
          
          int length = mat_two.length;
           int dimension = (int)Math.sqrt(length);
          
          int[]mat_result = new int[length];
          
          for (int i = 0; i < dimension; i++)
          {
              for (int j=0; j < dimension; j++)
              {
                  mat_result[i*dimension+j] = mat_one[i*dimension+j] + mat_two[i*dimension+j];
              }
              
              
          }
          
          return mat_result;
          
          
          
      }      
      
      public static int[] subtract(int[]mat_one, int[]mat_two)
      {
          
          // get size of input matrices to know result of final matrix
          
          int length = mat_two.length;
          int dimension = (int)Math.sqrt(length);
          int[] mat_result = new int[length];
          
          for (int i = 0; i < dimension; i++)
          {
              for (int j=0; j < dimension; j++)
              {
                  mat_result[i*dimension+j] = mat_one[i*dimension+j] - mat_two[i*dimension+j];
              }  
          }        
          return mat_result;  
      }
      
      
      public static void divide(int value, int value_two, int[] large, int[]small)
      {
          int length_small = small.length;
          int dimension_small = (int)Math.sqrt(length_small);
 
          int length_large = length_small*4;
          int dimension_large = (int)Math.sqrt(length_large);

          
          for (int i =0, j = value; i < dimension_small; i++, j++)
          {
              for (int k =0,  l = value_two; k < dimension_small; k++, l++)
              {
                 small[i*dimension_small+k] = large[j*dimension_large+l]; 
              }
              
              
          }
      }
 
      public static void combine(int value, int value_two, int[] small, int[]large)
      {
          int length_small = small.length;
          int dimension_small = (int)Math.sqrt(length_small);

          int length_large = length_small*4;
          int dimension_large = (int)Math.sqrt(length_large);

          
          for (int i =0, j = value; i < dimension_small; i++, j++)
          {
              for (int k =0,  l = value_two; k < dimension_small; k++, l++)
              {
                  large[j*dimension_large+l] = small[i*dimension_small+k]; 
              }
              
              
          }
      }
      
      
      
      public static int[] strassen_multiplication(int matrix_one[], int matrix_two[])      
      
      {
          
          int length = matrix_one.length; 

          

          int[] matrix_final= new int[length];
          
          // base case is if length = 1
          
          // experiment with different cases
          
          if (length <= 75*75) //n_o = 75
          {
               matrix_final = simple_multiplication(matrix_one,matrix_two,matrix_final, (int)Math.sqrt(length));
              
              //matrix_final[0] = matrix_one[0] * matrix_two[0];
              
          }
          
          else
          {
              
              // citation: youtube video about Strassen - divide into A11 ..... B22
              
              // create the sub matrices
            int[] A11 = new int[length/4];

            int[] A12 = new int[length/4];

            int[] A21 = new int[length/4];

            int[] A22 = new int[length/4];

            int[] B11 = new int[length/4];

            int[] B12 = new int[length/4];

            int[] B21 = new int[length/4];

            int[] B22 = new int[length/4];   
            
          // divide the matrix into 4 matrices of size n/2
            
             divide(0 , 0, matrix_one, A11); 
             divide((int)Math.sqrt(length)/2, 0, matrix_one,A21);
             divide(0,(int)Math.sqrt(length)/2, matrix_one, A12);
             divide((int)Math.sqrt(length)/2,(int)Math.sqrt(length)/2, matrix_one, A22);
     
            divide(0 , 0, matrix_two, B11);
            divide(0, (int)Math.sqrt(length)/2, matrix_two, B12);
            divide((int)Math.sqrt(length)/2, 0, matrix_two, B21);
            divide((int)Math.sqrt(length)/2, (int)Math.sqrt(length)/2, matrix_two, B22);
                           
            
            /*
             * 
             * 
             *   Unused method of saving space by reducing submatrices.
             *  Create an object class A11 in which you store row start, row end, col start and col end. This way, don't need 8 submatrices.
              A11 dataA11 = new A11(0, length/2, 0, length/2);
              A11 dataA12 = new A11(0, length/2, length/2, length);
              A11 dataA21 = new A11(length/2, length, 0, length/2);
              A11 dataA22 = new A11(length/2, length, length/2, length);

              int[][] P1 = strassen_multiplication(add(dataA11, dataA22, length/2, matrix_one), add(dataA11, dataA22, length/2, matrix_two));
 
              int[][] P2 = strassen_multiplication(add(dataA21, dataA22, length/2, matrix_one), add(dataA11, length/2, matrix_two));

              int[][] P3 = strassen_multiplication(add(dataA11, length/2,matrix_one), subtract(dataA12, dataA22, length/2, matrix_two));
            
              int[][] P4 = strassen_multiplication(add(dataA22, length/2, matrix_one), subtract(dataA21, dataA11, length/2, matrix_two));

             int[][]P5 = strassen_multiplication(add(dataA11, dataA12, length/2, matrix_one), add(dataA22, length/2, matrix_two)); 
           
             int[][] P6 = strassen_multiplication(subtract(dataA21, dataA11, length/2, matrix_one), add(dataA11, dataA12, length/2, matrix_two));
    
              int[][] P7 = strassen_multiplication(subtract(dataA12, dataA22, length/2, matrix_one), add(dataA21, dataA22, length/2, matrix_two));
            
            */
            
            
            

            
            // DO THIS RECURSIVELY
           int [] P1 = strassen_multiplication(add(A11, A22), add(B11, B22));

            int [] P2 = strassen_multiplication(add(A21, A22), B11);

            int [] P3 = strassen_multiplication(A11, subtract(B12, B22));

            int [] P4 = strassen_multiplication(A22, subtract(B21, B11));

            int [] P5 = strassen_multiplication(add(A11, A12), B22);

            int [] P6 = strassen_multiplication(subtract(A21, A11), add(B11, B12));

            int [] P7 = strassen_multiplication(subtract(A12, A22), add(B21, B22));
            
            
            // use the addition/ subtraction formulas to get result matrices
            
            
            
            int [] R11 = add(subtract(add(P1, P4), P5), P7);

            int [] R12 = add(P3, P5);

            int [] R21 = add(P2, P4);

            int [] R22 = add(subtract(add(P1, P3), P2), P6);
            
            
            /*   Unused method of saving space by not using intermediate matrices
             * 
             * 
             * combine(0,0, (add(subtract(add(P1, P4), P5), P7)), matrix_final);
            combine(0, length/2, add(P3, P5), matrix_final);
            combine(length/2, 0, add(P2, P4), matrix_final);
            combine(length/2, length/2, add(subtract(add(P1, P3), P2), P6), matrix_final);
            
            *
            */
            
            combine(0,0, R11, matrix_final);
            combine(0,(int)Math.sqrt(length)/2, R12, matrix_final);
            combine((int)Math.sqrt(length)/2, 0, R21, matrix_final);
            combine((int)Math.sqrt(length)/2, (int)Math.sqrt(length)/2, R22, matrix_final);
            
            

      }

          return matrix_final;
          
      }
      
    public static void main(String[] args)
    {     
        String d=args[1];
        int dimension;
        dimension=Integer.parseInt(d);
        
        
        double dimension_root = Math.sqrt(dimension);
 
        String input_file=args[2];
        
        int dimension_old = dimension;
        
        if (!isPowerOfTwo(dimension))
        {
            dimension = nextPowerOf2(dimension);
            
        }
        
        int[] matrix_one = new int[dimension*dimension];
        int[] matrix_two = new int[dimension*dimension];
        int[] matrix_final = new int[dimension*dimension];


      /* matrices for testing purposes          
        for (int i = 0; i < dimension*dimension; i++)
        {
            matrix_one[i] =0;
            matrix_two[i]=1;
        }
        */
  
     try
     {
         Scanner console = new Scanner(new File(input_file));
    
         while (console.hasNext())
         {

             // get both matrices
             
             for (int i = 0; i < dimension_old; i++)
             {
                 for (int j = 0; j < dimension_old; j++)
                 {
                     
                 String num = console.nextLine();
                 int number = Integer.parseInt(num);
                 
                 matrix_one[i*dimension+j] = number;
                 
                 
                 }
                 for (int k = dimension_old; k < dimension; k++)
                     matrix_one[i*dimension+k] = 0;
                 
             }
             
                          for (int i = dimension_old; i < dimension; i++)
             {
                 for (int j = 0; j < dimension; j++)
                 {
                     matrix_one[i*dimension+j] = 0;
                     
                 }
                 
                          }
 
             
             for (int i = 0; i < dimension_old; i++)
             {
                 for (int j = 0; j < dimension_old; j++)
                 {
                     
                 String num = console.nextLine();
                 int number = Integer.parseInt(num);
                 
                 matrix_two[i*dimension+j] = number;
                 
                 }
                                  for (int k = dimension_old; k < dimension; k++)
                     matrix_two[i*dimension+k] = 0;
             }
                                       for (int i = dimension_old; i < dimension; i++)
             {
                 for (int j = 0; j < dimension; j++)
                 {
                     matrix_one[i*dimension+j] = 0;
                     
                 }
                 
                          }
         }      
  
       }
     
      catch (FileNotFoundException ex)  
      {

        System.out.println("No file found!");
      }
      
     
                 
      // time simple multiplication
                  
        /*      
      long startTime = System.nanoTime();

     simple_multiplication(matrix_one, matrix_two, matrix_final, dimension);
     
     long endTime = System.nanoTime();

long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
   System.out.println("Simple multiplication took " + duration/1000000 + " ms"); 
   
*/
          

// time strassen multiplication

      // long startTime_two= System.nanoTime();

 
   int[] result =  strassen_multiplication(matrix_one, matrix_two); 
   /*
        long endTime_two = System.nanoTime();
        long duration_two = (endTime_two - startTime_two);  //divide by 1000000 to get milliseconds.
        System.out.println("Strassen multiplication took " + duration_two/1000000 + " ms");

  */

      
                for (int i = 0,  j = 0; i < dimension_old; i++, j++)
    {
        System.out.println(result[i*dimension+j]);
        
        
    }

      
    }
    
    
       
  }



    

