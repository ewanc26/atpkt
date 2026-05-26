{
  description = "atpkt: A professional-grade, modular ATProtocol SDK for Kotlin.";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
        jdk = pkgs.jdk21;
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = [
            jdk
            pkgs.gradle
            pkgs.git
            pkgs.gh
          ];

          shellHook = ''
            export JAVA_HOME=${jdk}
            export PATH=$JAVA_HOME/bin:$PATH
            echo "atpkt development environment ready (OpenJDK 21 + Gradle)"
          '';
        };
      }
    );
}
