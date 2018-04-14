$ ->
  window.Glob ?= {}
  apiUrl =
    reg: '/signup/'
    reg: '/users'

  handleError = (error) ->
      alert('something went wrong')

  vm = ko.mapping.fromJS
    email:''
    login:''
    psw: ''


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
      users = response.users
      console.log(users)
      vm.users(users)
      console.log(vm.users())
      console.log(vm.users().length)

  ko.applyBindings {vm}
