# MythicPapi
Now you can use PlaceholderAPI's placeholder as MythicMobs skill condition!

## Dependencies
* [Mythicmobs](https://dev.bukkit.org/projects/mythicmobs/files)
* [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Usage
[You can find valid Placeholder here](https://www.spigotmc.org/wiki/placeholderapi-placeholders/). And of course any your own made expansion's placeholder.
But make sure you have loaded all the expansions that you want to use!  
**NOTE:** Mobs do not have placeholder! Because they are not player!

* papi{pl = placeholder1;o = operator;pr = placeholder2}
    * **pl** one of the placeholder/value you want to compare.
    * **o** the operator you want to use. Valid operator: 
        * == (You can use "<&eq>" to replace "=", but why not "="? :P)
        * !=
        * <=
        * \>=
        * <
        * \>  
        **NOTE** You can only use "==" and "!=" to compare not numeric value. Or it will always return false.  
        For example:  
        [×] papi{pl = %player_name%;o = >;pr = Narcissu14}  
        [√] papi{pl = %player_level%;o = <=;pr = 16}
    * **pr** another placeholder/value you want to compare.
    
## TODO
* Let me know your ideas! :D

## THANKS
* Thanks @xikage for MythicMobs. MythicMobs is aweeeeeeeesome!
* Thanks @BerndiVader for his example tutorial of custom condition!