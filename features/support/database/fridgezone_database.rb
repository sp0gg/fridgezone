class FridgezoneDatabase
  require 'active_record'

  def self.establish_connection
    ActiveRecord::Base.establish_connection(
        :adapter => 'mysql',
        :host => 'localhost',
        :username => 'fridgezoneUser',
        :password => 'gandalf',
        :database => 'fridgezone_dev'
    )
  end

  def self.cleanup
    Item.cleanup
    Tag.cleanup
  end

end