require 'rspec'
require 'page-object'

Selenium::WebDriver::Firefox::Binary.path='/opt/firefox/firefox'

World(PageObject::PageFactory)