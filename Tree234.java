// Project 2 for CS 3345
// Programmer: Remnin Ezekiel Ferrer 
// 234 Tree program that demonstrates insertion

class Data {
   public double data;        
   
   // Constructor
   public Data(double dd) {
	   data = dd; 
   }
   
   // Prints Item
   public void printItem() { 
	   System.out.print(" | " + data); 
   }
}  

class Node {
   private static final int NUM = 4;
   private int items;
   private Node parent;
   private Node childArr[] = new Node[NUM];
   private Data itemArr[] = new Data[NUM - 1];

   // Parent Node adopts an item
   public void adoptChild(int childNum, Node child) {
      childArr[childNum] = child;
      if(child != null)
         child.parent = this;
   }

   // Child loses Node parent
   public Node orphanChild(int childNum) {
      Node tempNode = childArr[childNum];
      childArr[childNum] = null;
      return tempNode;
   }
   
   // Gets current child
   public Node getChild(int childNum){
	    return childArr[childNum]; 
   }
   
   // Gets current parent
   public Node getParent(){ 
	   return parent; 
   }

   // Checks if child array exists or if node is a leaf
   public boolean isLeaf() { 
	   return (childArr[0]==null) ? true : false; 
   }
   
   // Gets current number of items
   public int getNumItems() { 
	   return items; 
   }

   // Gets data at a given index
   public Data getItem(int index) { 
	   return itemArr[index]; 
   }

   // Checks if node is 4 node
   public boolean isFull() { 
	   return (items==NUM-1) ? true : false; 
   }

   // Searches for item with given key
   public int findItem(double key) {                                    
      for(int j=0; j<NUM-1; j++) {                                 
         if(itemArr[j] == null)          
            break;
         else if(itemArr[j].data == key)
            return j;
      }
      return -1;
   }  

   // Inserts an item to a node
   public int insertItem(Data toAdd) {
   
      items++;                          
      double newKey = toAdd.data;       

      for(int j=NUM-2; j>=0; j--)        
         {                                 
         if(itemArr[j] == null)          
            continue;                      
         else                              
            {                              
            double itsKey = itemArr[j].data;
            if(newKey < itsKey)            
               itemArr[j+1] = itemArr[j]; 
            else
               {
               itemArr[j+1] = toAdd;   
               return j+1;                 
               }                           
            }  
         }  
      itemArr[0] = toAdd;              
      return 0;
   }  

   // Removes largest item from nonempty Node
   public Data removeItem() { 
      Data temp = itemArr[items-1];  
      itemArr[items-1] = null;           
      items--;               
      
      return temp;                            
      }
   
   // Prints a single node
   public void displayNode() {
      for(int j=0; j < items; j++)
         itemArr[j].printItem();   
      System.out.println(" |");         
   }
}  

class Tree {
   private Node root = new Node();           
   
   // Finds a node
   public int find(double key) {
      Node currentNode = root;
      int childNumber;
      while(true) {
         if(( childNumber=currentNode.findItem(key) ) != -1)
            return childNumber;               
         else if( currentNode.isLeaf() )
            return -1;                        
         else                                 
            currentNode = getNextChild(currentNode, key);
         }  
   }

   // Insert a DataItem, calls split function if necessary
   public void insert(double val) {
	   
      Node currentNode = root;
      Data toInsert = new Data(val);

      while(true) {
         if( currentNode.isFull() ) {
            splitFullNode(currentNode);               
            currentNode = currentNode.getParent();    
            currentNode = getNextChild(currentNode, val);
         }  

         else if( currentNode.isLeaf() )      
            break;                            
         
         else
            currentNode = getNextChild(currentNode, val);
      }  

      currentNode.insertItem(toInsert);       
   }  

   // Splits full 4 node
   public void splitFullNode(Node thisNode) {
      
      Data itemB, itemC;
      Node parent, child2, child3;
      int itemIndex;
      
      itemC = thisNode.removeItem();    
      itemB = thisNode.removeItem();    
      child2 = thisNode.orphanChild(2); 
      child3 = thisNode.orphanChild(3); 

      Node newRight = new Node();       

      if(thisNode==root) {
         root = new Node();                
         parent = root;                    
         root.adoptChild(0, thisNode);   
         }
      else {
    	 parent = thisNode.getParent();
      }
            
      itemIndex = parent.insertItem(itemB); 
      int n = parent.getNumItems();         

      for(int j=n-1; j>itemIndex; j--) {                                      
         Node temp = parent.orphanChild(j); 
         parent.adoptChild(j+1, temp);        
       }
      parent.adoptChild(itemIndex+1, newRight);

      newRight.insertItem(itemC);       
      newRight.adoptChild(0, child2); 
      newRight.adoptChild(1, child3); 
   }  
   
   // Gets next corresponding child using value
   public Node getNextChild(Node theNode, double val){

      int j;
      // assumes node is not empty, not full, not a leaf
      int numItems = theNode.getNumItems();
      for(j=0; j<numItems; j++) {                               
         if( val < theNode.getItem(j).data )
            return theNode.getChild(j);  
         }  
      return theNode.getChild(j);        
      }
   
   // Calls displayTree function and assigns parameters to corresponding values
   public void callPrintTree() {
      System.out.println("Using Preorder Traversal");
	  printTree(root, 0, 0);
   }
   
   // Prints Tree
   private void printTree(Node currentNode, int level, int childNumber) {
      System.out.print("current node: "+ level );
      
      if (level == 0) {
    	  // Don't print because root, not a child
      }
      else {
    	  System.out.print(" child position: " + childNumber +" ");
      }
      
      currentNode.displayNode();               
      
      int numItems = currentNode.getNumItems();
      for(int j=0; j<numItems+1; j++)
         {
         Node nextNode = currentNode.getChild(j);
         if(nextNode != null)
            printTree(nextNode, level+1, j);
         else
            return;
         }
    }  
} 

public class Tree234 {	
	public static void main (String args[]) {
	
	    Tree tree = new Tree();

	    tree.insert(1);
	    tree.insert(12);
	    tree.insert(8);
	    tree.insert(2);
	    tree.insert(25);
	    tree.insert(6);
	    tree.insert(14);
        tree.insert(28);
	    tree.insert(17);
	    tree.insert(7);
	    tree.insert(52);
	    tree.insert(16);
	    tree.insert(48);
	    tree.insert(68);
	    tree.insert(3);
	    tree.insert(26);
	    tree.insert(29);
	    tree.insert(53);
	    tree.insert(55);
	    tree.insert(45);
	    
	    tree.callPrintTree();
	}
}
