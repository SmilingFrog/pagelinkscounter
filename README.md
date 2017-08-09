# pagelinkscounter
Count the number of unique links on an HTML page

- Jsoup library is used to parse HTML
- com.sergiisavin.App class is executable (contains main) and allows to see the work of the programm
- Exceptions are not processed. Since external library is used, more work is required to process all exceptions correctly

Main components of the Program:
- PageLoader allows to load the page from a valid URL into a String
- LinksExtractor extracts links into a Set of Strings. Only links with http protocol are extracted
- HTMLProcessor extracts all links for the domain
- LinksCounter counts all links for the domain

