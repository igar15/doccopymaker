[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2dc727dad26e4b7b978dbf16c0f16e7b)](https://www.codacy.com/gh/igar15/doccopymaker/dashboard)
[![Build Status](https://app.travis-ci.com/igar15/doccopymaker.svg?branch=master)](https://app.travis-ci.com/github/igar15/doccopymaker)

Document Copy Maker project 
=================================

This is the implementation of Document Copy Maker project used in the technical documentation department to create copies
of electronic documents, specified in the consignment note.    

### Technology stack used: 
* Maven
* Apache POI
* Swing
* JUnit 5

### Project key logic:
* System main purpose: Creation of copies of electronic documents specified in the consignment note.
* Users select a consignment note file (.doc and .docx file types are supported), and a directory, where to write copies of documents.
* The program reads the сonsignment note, extracts the decimal numbers of electronic documents from it, and then creates paths 
to the files of these documents, which are stored in the electronic archive of the enterprise. After that, the program creates
copies of electronic documents (using the created paths) in the selected directory.
* Graphical interface allows the user to monitor the progress of the program.
