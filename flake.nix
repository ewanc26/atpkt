{
  description = "atpkt: AT Protocol SDK for Kotlin — JVM-first, Ktor-based.";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};
        jdk = pkgs.jdk25;
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
            echo "atpkt development environment ready (OpenJDK 25 + Gradle)"
          '';
        };
      }
    );
}
