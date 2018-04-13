$ ->
  window.Glob ?= {}
  apiUrl =
    reg: '/signup/'

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

  ko.applyBindings {vm}
