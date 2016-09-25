class Login
  include PageObject

  page_url 'http://localhost:9510/Fridgezone/login'

  text_field(:username, :name => 'username')
  text_field(:password, :name => 'password')

  button(:login, :name => 'submit')

end