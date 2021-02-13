import { useCallback, useEffect, useState } from 'react'
import { IoReturnUpBackOutline } from 'react-icons/io5'
import { Link, useLocation } from 'react-router-dom'
import { File } from './components/File'
import { Folder } from './components/Folder'

export const App = () => {
    const { pathname } = useLocation()

    const [data, setData] = useState(null)
    const [error, setError] = useState(false)

    const fetchData = useCallback(async () => {
        setData(null)
        setError(false)

        const response = await fetch(`http://localhost:8085${pathname}`)

        if (!response.ok) {
            console.log(response)
            setError(true)
            return
        }

        const resData = await response.json()

        setData(resData)
    }, [pathname])

    let dir
    if (pathname !== '/') {
        dir = pathname.split('/').slice(0, -1).join('/') || '/'
    }

    useEffect(() => {
        fetchData()
    }, [fetchData, pathname])

    return (
        <ul>
            <li>
                <p>Current folder: {pathname}</p>
            </li>
            <>
                {data && (
                    <>
                        {dir && (
                            <li className='back'>
                                <Link to={dir}>
                                    <IoReturnUpBackOutline />
                                </Link>
                            </li>
                        )}
                        {data.files.map(entry => (
                            <li key={entry.path}>
                                {entry.folder ? <Folder folder={entry} /> : <File file={entry} />}
                            </li>
                        ))}
                    </>
                )}
                {error && (
                    <li className='error'>
                        <h3>Error occurred</h3>
                        <Link to='/'>Get back to home</Link>
                    </li>
                )}
            </>
        </ul>
    )
}
