# Distributed-Computing-Demo-with-JavaFX-

The aim of this demo is to implement a distributed system for a major computer manufacturer and distributor called Banana using JavaRMI. 
This UI will use Javafx. A user- friendly interface created with JavaFx technology is designed to allow an easier understanding and to help the research of the distributed computing.

Banana builds smartphones, laptops and desktop computers, which are then sold in Banana owned shops around the world.

The Java based practical aspect of this module involves building a distributed system using Java RMI. 

Below I detail the three levels of my functionalities for everyone to study:

Basic
- Each Banana owned shop has its own Branch Server. The Branch Server should store information regarding
the products that shop sells and their stock levels. It should also store customer information
such their name and purchase history.
- Each Banana owned shop will have multiple tills (or Branch Clients) where a salesperson can access
stock levels and customer information aswell as confirm product sales.
I develop the Branch Server and Branch Client using Java RMI. Note that the information I decide
to store on the Branch Server is not particularly important, I should focus on working out the architecture
of the distributed system.

Intermediate 
- All Banana products (smartphones, laptops and desktop computers) are built and stored at Banana Headquarters.
Information regarding the number of each product in storage is kept on the Product Server at
Banana HQ.
- Consider when a customer wants to buy a product that is out of stock in a particular Banana shop, or
wants to buy more stock than is currently available. The salesperson should be able to view all available
products (even if that shop doesn’t yet stock them) and request additional stock from Banana HQ via
their till (Branch Client).
I develop the Product Server using Java RMI, and edit my Branch Server and Branch Client from
the basic requirements in order to add this additional functionality.

Advanced 
- The number of Banana owned shops has grown rapidly, and the single Product Server at Banana HQ is
now being overwhelmed with requests from shops. All traffic to Banana HQ should be passed through
a Load Balancer, which should then split the requests it receives between multiple Product Servers.
I develop the Load Balancer using Java RMI, and edit my Branch Server and Product Server from
the intermediate requirements as appropriate. For simplicity I suggest that each Product Server should deal
with unique products. I.e. One Product Server contains only smartphone products, another Product Server
contains only laptop products. Then the Load Balancer can split requests based on product type.

