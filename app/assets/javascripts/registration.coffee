$ ->
  window.Glob ?= {}
  apiUrl =
    reg: '/register/'
    users: '/get-users/'

  handleError = (error) ->
      alert('something went wrong')

  vm = ko.mapping.fromJS
    email:''
    psw: ''
    comment: ''
    slanguages: []
    planguage: ''


  vm.onSubmit = ->
    console.log('email', vm.email())
    console.log('slanguages', vm.slanguages())
    console.log('planguage', vm.planguage())
    console.log('psw', vm.psw())

    console.log('comment', vm.comment())
    $.ajax
      url: apiUrl.reg
      type: 'POST'
      data: JSON.stringify(
        email: vm.email()
        psw: vm.psw()
        comment: vm.comment()
        sLanguages: vm.slanguages()
        pLanguage: vm.planguage()
      )
      dataType: 'json'
      contentType: 'application/json'
    .fail handleError
    .done (response) ->
        alert(response)

      object HomeController {
        case class Users(email: String, login: String, createdAt: Date)
  var UsersList = List.empty[Users]
  val user1 = Users(
    email = "asdf@asd.dd",
    login = "asdf",
    createdAt = new Date
  )
  val user2 = Users(
    email = "shoxrux0390.@mail.ru",
    login = "shoxrux123",
    createdAt = new Date
  )
  val user3 = Users(
    email = "shoxruxxudoynazarov0390.@mail.ru",
    login = "banana123",
    createdAt = new Date
  )
  val user4 = Users(
    email = "xudoynazarov0390.@mail.ru",
    login = "banana0390",
    createdAt = new Date
  )
  val user5 = Users(
    email = "java0390.@gmail.com",
    login = "scala123",
    createdAt = new Date
  )
  val user6 = Users(
    email = "scala.@gmail.com",
    login = "scala321",
    createdAt = new Date
  )
  val user7 = Users(
    email = "javascala931.@mail.com",
    login = "person931",
    createdAt = new Date
  )
  val user8 = Users(
    email = "banana0390.@bk.ru",
    login = "apple123",
    createdAt = new Date
  )
  val user9 = Users(
    email = "apple931.@mail.ru",
    login = "cherry",
    createdAt = new Date
  )
  val user10 = Users(
    email = "person.@gmail.com",
    login = "user123",
    createdAt = new Date
  )
}

  vm.report = ->
    $.ajax
      url: apiUrl.users
      type: 'GET'
    .fail handleError
    .done (response) ->
      users = response.users
      console.log(users)
      vm.users(users)
      console.log(vm.users())
      console.log(vm.users().length)

      ko.applyBindings {vm}