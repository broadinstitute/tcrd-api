package tcrd_api.server.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import tcrd_api.server.AkkaHttpHelper._
import tcrd_api.server.model.Body
import tcrd_api.server.model.inline_response_200
import tcrd_api.server.model.inline_response_200_1

class DefaultApi(
    defaultService: DefaultApiService,
    defaultMarshaller: DefaultApiMarshaller
) {
  import defaultMarshaller._

  lazy val route: Route =
    path("filter") { 
      post {
        
          
            
              
                entity(as[Body]){ body =>
                  defaultService.filterPost(body = body)
                }
             
           
         
       
      }
    } ~
    path("status") { 
      get {
        
          
            
              
                
                  defaultService.getStatus()
               
             
           
         
       
      }
    } ~
    path("meta") { 
      get {
        
          
            
              
                
                  defaultService.metaGet()
               
             
           
         
       
      }
    }
}

trait DefaultApiService {

  def filterPost200(responseList[String]: List[String]): Route =
    complete((200, responseList[String]))
  /**
   * Code: 200, Message: Success!, DataType: List[String]
   */
  def filterPost(body: Body): Route

  def getStatus200(responseinline_response_200: inline_response_200)(implicit toEntityMarshallerinline_response_200: ToEntityMarshaller[inline_response_200]): Route =
    complete((200, responseinline_response_200))
  /**
   * Code: 200, Message: Success!, DataType: inline_response_200
   */
  def getStatus()
      (implicit toEntityMarshallerinline_response_200: ToEntityMarshaller[inline_response_200]): Route

  def metaGet200(responseList[inline_response_200_1]: List[inline_response_200_1])(implicit toEntityMarshallerList[inline_response_200_1]: ToEntityMarshaller[List[inline_response_200_1]]): Route =
    complete((200, responseList[inline_response_200_1]))
  /**
   * Code: 200, Message: Success!, DataType: List[inline_response_200_1]
   */
  def metaGet()
      (implicit toEntityMarshallerList[inline_response_200_1]: ToEntityMarshaller[List[inline_response_200_1]]): Route

}

trait DefaultApiMarshaller {
  implicit def fromRequestUnmarshallerBody: FromRequestUnmarshaller[Body]


  implicit def toEntityMarshallerinline_response_200: ToEntityMarshaller[inline_response_200]

  implicit def toEntityMarshallerList[inline_response_200_1]: ToEntityMarshaller[List[inline_response_200_1]]

}

