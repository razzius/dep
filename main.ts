import { args } from 'deno'

async function getJson(url) {
    const data = await fetch(url)
    return await data.json()
}

async function main(packageName) {
    const data = await getJson(`https://clojars.org/api/artifacts/${packageName}`)
    console.log(`${packageName} {:mvn/version "${data.latest_release}"}`)
}

main(args[1])
