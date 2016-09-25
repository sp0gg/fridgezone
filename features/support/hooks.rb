require 'watir-webdriver'

include BrowserFactory

single_browser_instance = build_firefox

Before do
  FridgezoneDatabase.establish_connection
  FridgezoneDatabase.cleanup
  @browser = single_browser_instance
  @browser.cookies.clear
end

After do
  FridgezoneDatabase.establish_connection
  FridgezoneDatabase.cleanup
  single_browser_instance.refresh
end

at_exit do
  FridgezoneDatabase.cleanup
  single_browser_instance.close
end