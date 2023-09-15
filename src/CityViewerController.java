import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.util.Iterator;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


public class CityViewerController {
    //create variables for the different buttons, text fields, labels, and combination boxes
    @FXML
    private Label directDistance;
    @FXML
    private ComboBox<Vertex> dstCityCombo;
    @FXML
    private Label fileReadMsg;
    @FXML
    private TextField filenameInput;
    @FXML
    private Label lengthOfPath;
    @FXML
    private Label numOfCities;
    @FXML
    private Label numOfPaths;
    @FXML
    private TextArea pathsScrollArea;
    @FXML
    private Button readFileButton;
    @FXML
    private ComboBox<Vertex> srcCityCombo;

    //create local data fields
    private Graph<Vertex, City> graph = null;
    private OrderedAddOnce<Vertex> vertexList = null;
    private boolean sourceCitySet = false;
    private boolean destinationCitySet = false;
    
    @FXML
    void $readFileButton(ActionEvent event) {
        //set the initial variables
        String fileName = filenameInput.getText();
        File dataFile = null;
        Scanner inputFileData = null;
        String word = "";
        int numOfLinesRead = 0;
        graph = new Graph<>();
        vertexList = new OrderedAddOnce<>();
        
        //empty out all of the lists so that the list won't contain multiple copies even if you open the file multiple times
        fileReadMsg.setText("");
        directDistance.setText("");
        lengthOfPath.setText("");
        numOfCities.setText("");
        numOfPaths.setText("");
        pathsScrollArea.setText("");
        srcCityCombo.getSelectionModel().clearSelection();
        srcCityCombo.setValue(null);
        srcCityCombo.getItems().clear();
        srcCityCombo.getItems().removeAll();
        srcCityCombo.setVisibleRowCount(1);
        dstCityCombo.getSelectionModel().clearSelection();
        dstCityCombo.setValue(null);
        dstCityCombo.getItems().clear();
        dstCityCombo.getItems().removeAll();
        dstCityCombo.setVisibleRowCount(1);
        
        try {
            //ensure that the user input something
            if (fileName.equals("")) {
                throw new FileNotFoundException("You didn't input anything");
            }
            
            dataFile = new File(fileName);
            //prevent issues involving the file
            if (!(dataFile.exists()) || !(dataFile.canRead()) || (dataFile.isDirectory())) {
                throw new FileNotFoundException("File doesn't exist, can't be read, or is a directory");
            } else {
                 //read the file
                inputFileData = new Scanner (dataFile);
                while (inputFileData.hasNextLine()) {
                    //store the next line
                    String line = inputFileData.nextLine();
                    Scanner lineScanner = new Scanner(line);
                    lineScanner.useDelimiter(",");
                    numOfLinesRead += 1;
                    //ignore first line of column names
                    if ((numOfLinesRead > 1) && (!line.isBlank())) {
                        //split up the words within the line
                        int zip = lineScanner.nextInt();
                        String cityName = lineScanner.next();
                        cityName = cityName.strip();
                        String stateName = lineScanner.next();
                        stateName = stateName.strip();
                        double latitude = lineScanner.nextDouble();
                        double longitude = lineScanner.nextDouble();
                        int timezone = lineScanner.nextInt();
                        String daylightStr = lineScanner.next();
                        boolean yesDaylight = false;
                        if (daylightStr.charAt(0) == '1') {
                            yesDaylight = true;
                        }
                        //store everything in a city and throw it in the group
                        City tempCity = new City(zip, cityName, stateName, latitude,longitude,timezone,yesDaylight);
                        Vertex tempVertex = new Vertex(tempCity);
                        vertexList.addOnce(tempVertex);
                    }
                }
                inputFileData.close();
                //set the message color to a shade of green that I can see
                fileReadMsg.setTextFill(Color.web("#4BB543"));
                fileReadMsg.setText("The cities have been read");
                //put the vertices into the combo box and construct the graph
                Iterator<Vertex> vertexIter = vertexList.iterator();
                srcCityCombo.setVisibleRowCount(10);
                dstCityCombo.setVisibleRowCount(10);
                int numOfVerticesRead = 0;
                while (vertexIter.hasNext()) {
                    Vertex nextVertex = vertexIter.next();
                    srcCityCombo.getItems().add(nextVertex);
                    dstCityCombo.getItems().add(nextVertex);
                    numOfVerticesRead += 1;
                }
                graph = constructEdges(vertexList);
                //set the number of cities read and set color to blue
                numOfCities.setTextFill(Color.web("#483D8B"));
                numOfCities.setText(Integer.toString(numOfVerticesRead) + " Cities Read");
            }
        }
        //can be thrown by inputFileData = new Scanner (dataFile);
        catch (FileNotFoundException e){
            srcCityCombo.setVisibleRowCount(1);
            dstCityCombo.setVisibleRowCount(1);
            System.out.println("Scanner error opening the file " + fileName);
            System.out.println(e.getMessage());
            //set the message color to a more friendly red
            fileReadMsg.setTextFill(Color.web("#FF003C"));
            fileReadMsg.setText("The file was unsuccessfully read");
        } 
        //can be thrown by dataFile.exists() or dataFile.canRead()
        catch (SecurityException e) {
            srcCityCombo.setVisibleRowCount(1);
            dstCityCombo.setVisibleRowCount(1);
            System.out.println("File error opening the file " + fileName);
            System.out.println(e.getMessage());
            //set the message color to a more friendly red
            fileReadMsg.setTextFill(Color.web("#FF003C"));
            fileReadMsg.setText("The file was unsuccessfully read");
        }
    }
    
    @FXML
    void $readSrcComboBox(ActionEvent event) {
        sourceCitySet = true;
        if (!destinationCitySet) {
            dstCityCombo.getSelectionModel().clearSelection();
            dstCityCombo.setValue(null);
        }
        
        handlePaths();
    }
    
    @FXML
    void $readDstComboBox(ActionEvent event) {
        destinationCitySet = true;
        if (!sourceCitySet) {
            srcCityCombo.getSelectionModel().clearSelection();
            srcCityCombo.setValue(null);
        }
        
        handlePaths();
    }
    
    public void handlePaths () {
        Vertex sourceCityVertex = srcCityCombo.getValue();
        Vertex destinationCityVertex = dstCityCombo.getValue();
        DecimalFormat df = new DecimalFormat("#.#");
        
        //ensure that both combo boxes have been chosen
        if (sourceCityVertex != null && destinationCityVertex != null) {
            destinationCitySet = false;
            sourceCitySet = false;  
            
            //set the direct distance between the selected cities and set color to blue
            double distance = sourceCityVertex.distanceCalc(destinationCityVertex);
            directDistance.setTextFill(Color.web("#483D8B"));
            directDistance.setText("Direct: " + df.format(distance) + " Miles");
            
            //find the shortest path
            try {
            findAndPrintShortestPath(graph, sourceCityVertex, destinationCityVertex);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            directDistance.setText("");
            lengthOfPath.setText("");
            numOfPaths.setText("");
        }
    }
    
    private Graph<Vertex, City> constructEdges(OrderedAddOnce<Vertex> vertexList) {
        Graph<Vertex, City> graph = new Graph<>();
        ArrayList<Vertex> listOfVertices = new ArrayList<>();
        
        //add vertices to the graph
        for (Vertex vertex : vertexList) {
            graph.addVertex(vertex);
        }
       
        //convert the tree into an array list
        Iterator<Vertex> vertexIter = vertexList.iterator();
        while (vertexIter.hasNext()) {
            Vertex vertex = vertexIter.next();
            listOfVertices.add(vertex);
        }
        
        //add edges to the graph to connect all vertices
        for (int i = 0; i < listOfVertices.size(); i += 1) {
            Vertex currentVertex = listOfVertices.get(i);
            
            for (int j = 0; j < listOfVertices.size(); j += 1) {
                Vertex nextVertex = listOfVertices.get(j);
                
                //if path already exists, skip adding a new one
                if (graph.hasEdge(currentVertex, nextVertex)) {
                    continue;
                }
                
                //calculate distance between the two vertices and create edge if distance is below a certain threshold
                //max distance is set by refueling range of the drone
                double distance = currentVertex.distanceCalc(nextVertex);
                if (distance < 300) {
                    graph.addUndirectedEdge(currentVertex, nextVertex, distance);
                }
            }
        }
        
        return graph;
    }
    
    private void findAndPrintShortestPath(Graph<Vertex, City> graph, Vertex source, Vertex target) {
        double dist = 0;
        
        DijkstraWithPriorityQueue <Vertex, City> dSPPQ = new DijkstraWithPriorityQueue<>();
        List<Vertex> shortestPath = dSPPQ.findShortestPath(graph, source, target);
        
        System.out.println(shortestPath);
        System.out.println(dSPPQ.getNumOfPathsConsidered());
        System.out.println(dSPPQ.getPathsConsidered());
        
        if (shortestPath != null) {
            for (int i = 0; i < shortestPath.size() - 1; i += 1) {
                dist += shortestPath.get(i).distanceCalc(shortestPath.get(i+1));
            }
            //set number of paths considered and make it green
            numOfPaths.setTextFill(Color.web("#4BB543"));
            numOfPaths.setText("Paths Considered: " + dSPPQ.getNumOfPathsConsidered());
            //set distance of found path and make it blue
            DecimalFormat df = new DecimalFormat("#.#");
            lengthOfPath.setTextFill(Color.web("#483D8B"));
            lengthOfPath.setText("Path: " + df.format(dist) + " Miles");
            //add the latest path
            String newText = "PATH: \n";
            for (Vertex current : shortestPath) {
                newText += current.toString();
                newText += "\n";
            }
            newText += "\n";
            pathsScrollArea.appendText(newText);
        } else {
            //set number of paths considered and make it red
            numOfPaths.setTextFill(Color.web("#FF003C"));
            numOfPaths.setText("Paths Considered: " + dSPPQ.getNumOfPathsConsidered());
            String newText = "PATH: \nNone\n\n";
            pathsScrollArea.appendText(newText);
        }
    }
}
