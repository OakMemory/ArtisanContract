# ArtisanContract

A plugin that lets you make a contract with the Artisan.

## 🤔What's this?

> Oh my goodness! You seem to have a lot of amethyst shards that you can't even consume, but the book inside your
> backpack
> is starting to glow... Could the books make them into something interesting?
>
> There is always a voice whispering in your ears: stick your pickaxe on top of the ore, place the ore refinement on
> your
> chest, meditate on it, and the artisan will respond to you

You can use a certain cost to eliminate the time of digging for ore! Also you can repair the armor on your body for a
lower cost!

You can also convert almost useless amethyst shards with books and bookshelves!

## 🚀How to use?

### For server owner:

- Put this plugin to your plugin directory.
- Start your server.
- Check if it is loaded.
- If loaded, congratulations!

### For player

#### Refine

- Ask your server owner which tools are supported to refining
- Ask your server owner which blocks are supported to be refined
- If you want to burn logs, just sneak, and use your axes on logs
- If you want to smelting ores, just use your pickaxes on ores

#### Absorb

- Ask your server owner for absorb mappings
- Ask your server owner for item that can be used to absorb blocks called `block-absorber`
- Ask your server owner for block that can be used to absorb items called `absorber`
- If you want to absorb the block, sneak and then right-click on the block with the `absorber`
- If you want to absorb the item, please use the item directly right click "absorber", do not sneak

## 🔨Configurable

You can configure a lot of things to make this plugin work with the content you want.

- [x] New experience conversion
- [x] Whether the experience generated by the absorbed item cube can be used as mending
- [x] How much level cost to repair armors
- [x] How much point cost to refine ores
- [x] Which item to absorb block to experience
- [x] Which block to absorb item to experience
- [x] Mapping of items that can be melted by the pickaxe
- [x] Pickaxe that can be used for smelting
- [x] Axes that can be used for burn wood

Note: I'm not sure if I should add a burn mapping for axes, so a burn mapping for axes is not configurable. By default,
all blocks in the logs tag can be burned to charcoal by the axe.

## 🤗How to build

Just run this command.

```shell
gradlew reobfJar
```

NOTICE: All the code in this project is written in Kotlin, and there is a built-in Kotlin runtime library, so the
compilation result is large, which is normal.

NOTICE: This project is licensed under the MIT open source license agreement, which is extremely lenient. But I hope
you will still be able to carry a copy of the license agreement in projects that use it, and don't delete my name.