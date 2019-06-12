package tcrd_api.server.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import tcrd_api.server.AkkaHttpHelper._
import tcrd_api.server.model.FilterOptions
import tcrd_api.server.model.GenesFilterQuery
import tcrd_api.server.model.Status

class DefaultApi(
    defaultService: DefaultApiService,
    defaultMarshaller: DefaultApiMarshaller
) {
  import defaultMarshaller._

  lazy val route: Route =
    path("filterGenes") { 
      post {
        
          
            
              
                entity(as[GenesFilterQuery]){ body =>
                  defaultService.filterGenes(body = body)
                }
             
           
         
       
      }
    } ~
    path("possibleFilters") { 
      get {
        
          
            
              
                
                  defaultService.getPossibleFilters()
               
             
           
         
       
      }
    } ~
    path("status") { 
      get {
        
          
            
              
                
                  defaultService.getStatus()
               
             
           
         
       
      }
    }
}

trait DefaultApiService {

  def filterGenes200(responseList[String]: List[String]): Route =
    complete((200, responseList[String]))
  /**
   * Code: 200, Message: Success!, DataType: List[String]
   */
  def filterGenes(body: GenesFilterQuery): Route

  def getPossibleFilters200(responseList[FilterOptions]: List[FilterOptions])(implicit toEntityMarshallerList[FilterOptions]: ToEntityMarshaller[List[FilterOptions]]): Route =
    complete((200, responseList[FilterOptions]))
  /**
   * Code: 200, Message: Success!, DataType: List[FilterOptions]
   */
  def getPossibleFilters()
      (implicit toEntityMarshallerList[FilterOptions]: ToEntityMarshaller[List[FilterOptions]]): Route

  def getStatus200(responseStatus: Status)(implicit toEntityMarshallerStatus: ToEntityMarshaller[Status]): Route =
    complete((200, responseStatus))
  /**
   * Code: 200, Message: Success!, DataType: Status
   */
  def getStatus()
      (implicit toEntityMarshallerStatus: ToEntityMarshaller[Status]): Route

}

trait DefaultApiMarshaller {
  implicit def fromRequestUnmarshallerGenesFilterQuery: FromRequestUnmarshaller[GenesFilterQuery]


  implicit def toEntityMarshallerStatus: ToEntityMarshaller[Status]

  implicit def toEntityMarshallerList[FilterOptions]: ToEntityMarshaller[List[FilterOptions]]

}

