# TasksManager
 Android Todo list application - March Android Bootcamp Remote Application 
 
 # Pre-work - *TasksManager*

**TasksManager** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Sandy Ludosky**

Time spent: **35** hours spent in total
Extra hours to implement additional features

## User Stories

The following **required** functionality is completed:

* [ ] User can **successfully add and remove items** from the todo list
* [ ] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [ ] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [ ] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [ ] Improve style of the todo items in the list [using a recycler view](https://guides.codepath.com/android/using-the-recyclerview)
* [ ] Add support for completion due dates of todo items:  (and display within listview item) (due today, past due)
* [ ] Add support with RadioGroupButton for selecting the priority of each todo item (and display in listview item)
* [ ] Add support with Spinner for selecting status of each todo item (active, done) (and display in listview item)

* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:


* [ ] Added Launch screen
* [ ] Querying with “ASC” to sort tasks by date in listview
* [ ] List anything else that you can get done to improve the app functionality!
* [ ] Added Material Design and Floating Action Button library support
* [ ] Applying Color coding for priority levels : low, medium and high


## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.
SQLiteOpenHelper because of the lack of visibility on the data structure. I need to troubleshoot Android Device Monitor where I was unable to look at database records and 
I completed assignment with code refactoring. Upon implementing SQlitehelper database, I realized that I over complicated the code by adding enum custom data type in my Todo object Class. SQLite accepts data types such as : Text, real, int, none. So, I used helper methods to handle data type conversion back and forth : to save to the database and to fetch from database. I later refactored the code to include helper methods in 1 single class to avoid repetition.
Areas of improvement would be to understand and learn more about best practices of app structure and design patterns, which I think would help in many other areas of programming.

