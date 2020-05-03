import net.liftweb.json.JsonAST.JValue
import net.liftweb.json._
import com.typesafe.scalalogging.LazyLogging
class JsonToScala (private var json:JValue) extends LazyLogging {
  //the private members of the class
  private implicit val formats = DefaultFormats
  private var jValue:JValue= json

  //for the user if they dont know the type of the query
  def getScalaClass(): CaseClass= {
    logger.debug(getClass.toString+":: getScalaClass function returns CaseClass")
    val data = (jValue\"data").extract[Data]
    if(!data.repositoryOwner.isEmpty) return data.repositoryOwner.get
    if(!data.topic.isEmpty)  return data.topic.get
    if(!data.repository.isEmpty) return  data.repository.get
    if(!data.user.isEmpty) return data.user.get
    error("error")
  }

  //for users who know what type of queries will be returned
  def getUserQueryCaseClass():UserCase={
    logger.debug(getClass.toString+":: getUserQueryCaseClass function returns UserCase")
    (jValue\"data").extract[Data].user.get
  }
  def getRepoQueryCaseClass():RepositoryCase={
    logger.debug(getClass.toString+":: getRepoQueryCaseClass function returns RepositoryCase")
    (jValue\"data").extract[Data].repository.get
  }
  def getRepoOwnerQueryCaseClass():RepositoryOwnerCase={
    logger.debug(getClass.toString+":: getRepoOwnerQueryCaseClass function returns RepositoryOwnerCase")
    (jValue\"data").extract[Data].repositoryOwner.get
  }
  def getTopicQueryCaseClass():TopicCase={
    logger.debug(getClass.toString+":: getTopicQueryCaseClass function returns TopicCase")
    (jValue\"data").extract[Data].topic.get
  }


}

object JsonToScala {
  def apply(json:JValue): JsonToScala = new JsonToScala(json)
}
