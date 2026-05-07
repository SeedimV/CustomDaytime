# Custom Daytime Plugin

[![Download on Modrinth](https://img.shields.io/badge/Modrinth-Download-brightgreen?style=for-the-badge&logo=modrinth)](https://modrinth.com/plugin/custom-daytime)
[![Latest Release](https://img.shields.io/github/v/release/SeedimV/CustomDaytime?logo=github&logoColor=white&style=for-the-badge)](https://github.com/SeedimV/CustomDaytime/releases/latest)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://github.com/SeedimV/CustomDaytime?tab=GPL-3.0-1-ov-file#readme)

Customize Minecraft's day/night cycle and experience smoother nights with this lightweight plugin/mod!

> [!IMPORTANT]
> Since **v2.0.0**, the configuration file was changed from `config.yml` to `config.conf`.
> **Old `config.yml` files are not compatible and will not work.**
> The configuration format has also changed, so the old structure/syntax must be migrated manually.

## Features

- **Configurable Day & Night Lengths**  
  Set the duration of Minecraft's day and night in **minutes** via the config. Accepts decimal values for more precise timings (e.g., `0.5` for 30 seconds).

- **Accelerate Night While Sleeping**  
  Instead of instantly skipping the night, time will **smoothly accelerate** when enough players sleep. This feature can be disabled in config.

- **Easy-to-Use Config**  
  All values are adjustable in the `config.conf` file. All values are configured for each world separately.
  Custom Daytime supports multiple worlds. Copy the `"minecraft:overworld"` block, paste it at the end of the config, rename it to the target world key, and edit the values.
    ```hocon
    "minecraft:overworld" {
    # Length of the daytime cycle in minutes
    # Default: 10.0
    dayLength=10.0

    # Length of the nighttime cycle in minutes
    # Default: 10.0
    nightLength=10.0

    # Enable or disable night acceleration when enough players sleep
    # False: use vanilla sleep-to-skip-night mechanic
    # Default: true
    accelerationEnabled=true

    # Multiplier for night acceleration during sleep
    # Example: nightLength=10.0 (= 600 seconds) and AccelerationMultiplier=100.0 -> Acceleration lasts 6 seconds
    # Default: 100.0
    AccelerationMultiplier=100.0
  }

## License
This project is licensed under the [GNU General Public License v3.0](https://github.com/SeedimV/CustomDaytime/blob/master/LICENSE). You are free to use, modify, and distribute the project, but any derivative works must also be licensed under the same terms.
