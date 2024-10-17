package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.{TaskManager, Task}
import play.api.mvc.Flash


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def hello = Action { implicit request =>
    Ok(views.html.hello("Play Framework"))
  }




  val taskManager = new TaskManager()

  def listTasks() = Action { implicit request: Request[AnyContent] =>
    val tasks = taskManager.listTasks()
    Ok(views.html.tasklist(tasks)(request, request.flash)) // Передаем tasks, request и flash
  }



  def addTask() = Action { implicit request =>
    val description = request.getQueryString("description").getOrElse("")
    if (description.nonEmpty) {
      // Здесь добавьте логику добавления задачи
      taskManager.addTask(description)
    }
    Redirect(routes.HomeController.listTasks())
  }





  def completeTask(id: Int) = Action { implicit request: Request[AnyContent] =>
    taskManager.completeTask(id)
    Redirect(routes.HomeController.listTasks())
  }

  def removeTask(id: Int) = Action { implicit request: Request[AnyContent] =>
    taskManager.removeTask(id)
    Redirect(routes.HomeController.listTasks())
  }

  def addNewTasksToDB() = Action { implicit request =>
    taskManager.continueTasksToDb()
    Redirect(routes.HomeController.listTasks()).flashing("success" -> "Новая задача добавлена к уже существующим.")
  }

  def loadTasksFromDb() = Action { implicit request =>
    taskManager.getTasksFromDb()  // Загрузка задач из базы данных
    Redirect(routes.HomeController.listTasks()).flashing("success" -> "Задачи успешно загружены из базы данных.")
  }

  def refreshTasksInDb()=Action{implicit request =>
    taskManager.refreshTasksInDb()
    Redirect(routes.HomeController.listTasks()).flashing("success" -> "БД обновлена. Новые задачи загружены")
  }

  def clearTasks() = Action{ implicit request =>
    taskManager.clearTasks()
    Redirect(routes.HomeController.listTasks()).flashing("success" -> "Задачи удалены")

  }







}
