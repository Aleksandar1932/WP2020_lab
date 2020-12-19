# Лабораториска вежба бр.1 по предметот Веб програмирање 2020/21

## Oдговори на прашањата

Прашање 1:

На servlet-от BalloonOrder му ги испраќаме следните параметри
    
    size: Big
  
До колку одбереме Big како големина на балонот

Прашање 2:

На servlet-от ConfirmationInfo му ги испраќаме следните параметри
    
    clientName: Aleksandar Ivanovski
    
    clientAddress: ul. Ulica 1, 2400 Stumica
    
До колку во Balloon Order page како Client Name зададеме Aleksandar Ivanovski, и Delivery Address зададеме ul. Ulica 1, 2400 Stumica.

Прашање 3:

3.1 При избор на боја на балонот, т.е. при sumbit на `/` односно во `BalloonOrderServlet` со линијата `req.getSession().setAttribute("color", color);` прв пат сетираме cookie, бидејќи тогаш прв пат се внесува променлива во сесијата, a таа променлива се однесува на бојата на балонот.

![image](https://i.ibb.co/mJzcncv/Untitled.png)

3.2. При избор на големина на балонот т.е. при submit на `/selectBalloon` го користиме колачето `Cookie: JSESSIONID=6B7E2AA0064616421013A75F6F5C36F2`

3.3 Потоа при внесување на Delivery Info т.е. при submit на `/balloonOrder`, го користиме колачето `Cookie: JSESSIONID=6B7E2AA0064616421013A75F6F5C36F2` кое е всушност истото колаче кое беше сетирано во 3.1, а искористено и во 3.2.
