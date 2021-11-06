# AndroidCalculator
![image](https://user-images.githubusercontent.com/71960259/140609906-3d683dea-a5ab-4890-9344-49fba2ab13b0.png)

This Simple calculator was created for an Android app.
Providing the user the ability to perform simple  calculation tasks as Increase, Decrease multiply and Divide.

![image](https://user-images.githubusercontent.com/71960259/140609915-8077359d-fbea-4de2-b0e7-2fce46325138.png)


If some of the fields are not entered the buttons will be disabled.
If the second parameter is 0 then the button of diving will be  disabled (Because can't divide  by zero) 

There is a Seekbar at the bottom of the page. When the user will use the divide option and the number will not be an integer then the user will be able 
to change the amount of numbers after the dot that the current number will have. the seek bar is from 0 (the integer number ) to 5.
![image](https://user-images.githubusercontent.com/71960259/140609975-29b23ad4-2c49-412d-a980-e2002dd36b59.png)

If the user wants to decrease one number after the dot then the system will check if this number was bigger than five. is so then the number after him will be ceild up by 1.
![image](https://user-images.githubusercontent.com/71960259/140609968-4044902a-8811-4bc9-8fa6-8406c3165611.png)

Example when the seek bar is on 0:

![image](https://user-images.githubusercontent.com/71960259/140609986-0653ac77-9153-476c-9715-da9002d3f07b.png)


If the user wants to  get all of the numbers after the dot again then the original number will be displayed.

Example when the number is getting back as it was.
![image](https://user-images.githubusercontent.com/71960259/140609994-77cf92c1-5e52-44fe-96f4-d09866a192cf.png)

If the number is an integer then the seek bar will be set to 0 as the first time. If the user will increase the  seek bar then there will be added zero's to the answer.
* The seek bar will be disabled if nothign was entered and there is no result in the result field.
* There is a clear button when after clicking him all of the fields will be empty.
