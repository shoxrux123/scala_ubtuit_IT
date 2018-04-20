$ ->
  window.Glob ?= {}
  apiUrl =
    reg: '/signup/'
    users: '/report'

  handleError = (error) ->
      alert('something went wrong')

  vm = ko.mapping.fromJS
    email:''
    login:''
    psw: ''
    users: []


  vm.onSubmit = ->
    console.log('email', vm.email())
    console.log('login', vm.login())
    console.log('psw', vm.psw())

    console.log('comment', vm.comment())
    $.ajax
      url: apiUrl.reg
      type: 'POST'
      data: JSON.stringify(
        email: vm.email()
        login: vm.login()
        psw: vm.psw()
      )
      dataType: 'json'
      contentType: 'application/json'
    .fail handleError
    .done (response) ->
      alert(response)

  vm.report = ->
    $.ajax
      url: apiUrl.users
      type: 'GET'
    .fail handleError
    .done (response) ->
      for user in response
        user.createdAt = convertIntToDateTime(user.createdAt)
      users = response
      vm.users(users)

  vm.report()

  convertIntToDateTime = (intDate)->
    moment(parseInt(intDate)).format('MMM DD, YYYY HH:mm:ss')

  ko.applyBindings {vm}
